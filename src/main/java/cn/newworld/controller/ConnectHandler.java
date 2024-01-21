package cn.newworld.controller;

import cn.newworld.controller.processor.v1.UsersProcessor;
import cn.newworld.model.Request;
import cn.newworld.model.Response;
import cn.newworld.model.ServerConfig;
import cn.newworld.model.Whitelist;
import cn.newworld.util.Logger;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ConnectHandler implements Runnable {
    private static volatile boolean shutdownRequested = false; // 通知线程退出的标志

    public static void requestShutdown() {
        shutdownRequested = true;
    }

    @Override
    public void run() {
        int port = ServerConfig.getInstance().getPort();
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Logger.info("服务器正在监听端口 " + port + " ...");

            while (!shutdownRequested) {
                int numReadyChannels = selector.selectNow();
                if (numReadyChannels > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            handlerAccept(key);
                        } else if (key.isReadable()) {
                            handlerRead(key);
                        }
                        keyIterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            Logger.error("ConnectHandler 中出现错误: " + e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * 处理客户端连接事件。
     *
     * @param key 代表一个可选择通道和与之关联的选择器的注册
     * @throws IOException 处理连接事件时可能发生的 I/O 异常
     */
    private void handlerAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        InetSocketAddress clientAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
        String clientIP = clientAddress.getHostString();

        // 检查客户端IP是否在白名单中
        if (isInWhitelist(clientIP)){
            socketChannel.register(key.selector(),SelectionKey.OP_READ);
        } else {
            // 不在白名单，拒绝连接响应
            Logger.warning(clientIP+" has refused the connection！");
            socketChannel.close();

        }


    }

    /**
     * 检查客户端IP是否在服务器IP白名单中
     * @param clientIp 客户端 IP 字符串
     * @return 如果检查在白名单返回 true，不在就返回 false
     */
    private boolean isInWhitelist(String clientIp){
        List<String> whitelist = Whitelist.getInstance().getWhitelist();

        for (String ip : whitelist){
            if (ip.equals(clientIp)){
                return true;
            }
        }
        return false;
    }

    /**
     * 处理客户端可读事件。
     *
     * @param key 代表一个可选择通道和与之关联的选择器的注册
     * @throws IOException 处理可读事件时可能发生的 I/O 异常
     */
    private void handlerRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int bytesRead = socketChannel.read(buffer);
        if (bytesRead == -1) {
            Logger.info(socketChannel.getRemoteAddress() + " disconnected.");
            close(socketChannel,key);

        }
        if (bytesRead > 0){
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String requestMessage = new String(data, StandardCharsets.UTF_8);  // 客户端的请求消息
            Request request = new Request(requestMessage);

            String requestType = request.getRequestType();    // 客户端HTTP请求的方法
            String requestUrl = request.getUrl();   // 客户端HTTP请求的url路径
            String requestMessageBody = request.getRequestMessageBody();  // 客户端HTTP请求的消息体

            if (requestUrl.equals("/favicon.ico")){
                close(socketChannel,key);
                return;
            }
            Logger.info("Request source: "+socketChannel.getRemoteAddress()+" | Request type: "+requestType+ " | Request url: "+requestUrl);

            List<Processor> processorList = ProcessorManager.getProcessors();
            for (Processor processor : processorList){
                Class<? extends Processor> processorClass = processor.getClass();
                Method[] methods = processorClass.getDeclaredMethods();
                for (Method method : methods){
                    if (method.isAnnotationPresent(RequestMapping.class)){
                        if (method.getReturnType() == String.class){
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            for (Class<?> parameterType : parameterTypes){
                                if (parameterType == String.class){
                                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                                    String methodWithUrl = annotation.requestUrl();
                                    String methodWithRequestType = annotation.requestType().getToString();
                                    if (methodWithUrl.equals(requestUrl) && methodWithRequestType.equals(requestType)){
                                        try {
                                            String result = (String) method.invoke(processor,requestMessageBody);

                                            Response response = new Response(result);
                                            socketChannel.write(ByteBuffer.wrap(response.getResponseBody().getBytes(StandardCharsets.UTF_8)));
                                            Logger.info(socketChannel.getRemoteAddress() + " has returned a response.");
                                            close(socketChannel,key);

                                        } catch (Exception e){
                                            Logger.error(e.getMessage());

                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            close(socketChannel,key);
        }
    }

    private void close(SocketChannel socketChannel, SelectionKey key) throws IOException{
        socketChannel.close();
        key.cancel();
    }
}
