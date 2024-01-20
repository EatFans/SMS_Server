package cn.newworld.controller;

import cn.newworld.model.ServerConfig;
import cn.newworld.model.Whitelist;
import cn.newworld.util.Logger;
import org.reflections.Reflections;

import java.io.IOException;
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
            socketChannel.close();
            key.cancel();

        }
        if (bytesRead > 0){
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String requestMessage = new String(data, StandardCharsets.UTF_8);  // 客户端的请求消息
            /*
            * 从 requestMessage 中解析客户端发送的http请求，变成数组转化成每行，储存在requestLines字符串数组中。
            * 从 requestLines 数组中取出第一行，即HTTP请求消息的起始行，然后再次使用空格进行分割，得到新的字符串数组 requestLineParts，里面包含请求方法、请求路径等
            *
            * */
            String[] requestLines = requestMessage.split("\r\n");
            String[] requestLineParts = requestLines[0].split(" ");

            String requestMethod = requestLineParts[0];    // 客户端HTTP请求的方法
            String urlPath = requestLineParts[1];   // 客户端HTTP请求的url路径

            if (urlPath.equals("/favicon.ico")){
                socketChannel.close();
                key.cancel();
                return;
            }
            Logger.info("Request source: "+socketChannel.getRemoteAddress()+" | Request type: "+requestMethod+ " | Request url: "+urlPath);

            String basePackage = "cn.newworld.controller.processor.v1";
            Reflections reflections = new Reflections(basePackage);
            Set<Method> methods = reflections.getMethodsAnnotatedWith(RequestMapping.class);
            for (Method method : methods){
                if (method.getReturnType() == String.class){
                    // TODO: 检查请求的url与处理器注解的url是否一致，如果有相同的，就对应处理，没有就直接忽视
                }
            }


            // 检查客户端请求的是不是/api这个url，如果不是就直接断开连接，是就响应responseMessage
            switch (urlPath){
                case "/v1/login":
                    processLoginUrlRequest(socketChannel,key);
                    break;
                case "/v1/send":
                    processSendUrlRequest(socketChannel,key);
                    break;
                default:
                    socketChannel.close();
                    key.cancel();
                    break;
            }



        }
    }

    private void processLoginUrlRequest(SocketChannel socketChannel,SelectionKey key) throws IOException{
        String responseMessage = "HTTP/1.1 200 OK\r\n\r\n{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";
        socketChannel.write(ByteBuffer.wrap(responseMessage.getBytes(StandardCharsets.UTF_8)));
        Logger.info(socketChannel.getRemoteAddress() + " has returned a response.");
        socketChannel.close();
        key.cancel();

    }

    private void processSendUrlRequest(SocketChannel socketChannel,SelectionKey key) throws IOException {

    }

}
