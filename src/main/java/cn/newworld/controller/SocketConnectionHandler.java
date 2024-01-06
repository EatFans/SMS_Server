/**
 * 该类用于处理与客户端的连接，然后创建线程池用于对已经连接的客户端进行处理
 */
package cn.newworld.controller;

import cn.newworld.Application;
import cn.newworld.model.dao.Server;
import cn.newworld.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnectionHandler implements Runnable {
    private ExecutorService threadPool;

    private ServerSocket serverSocket;

    /**
     * 构造方法 初始化服务器套接字
     * @param port 端口
     * @throws IOException
     */
    public SocketConnectionHandler(int port) throws IOException {
        // 创建服务器套接字
        this.serverSocket = new ServerSocket(port);
        this.threadPool = Executors.newFixedThreadPool(Server.getServer().getConnectionMax());
        Logger.info("正在初始化网络连接...");
    }

    /**
     * 监听客户端连接线程，不断监听客户端的连接并向控制台输入日志信息
     */
    @Override
    public void run() {
        try {
            Logger.info("服务器已开放端口: " + serverSocket.getLocalPort());
            Logger.info("已经连接的客户端数量："+Server.getServer().getClientAmount());
            serverSocket.setSoTimeout(5000); // 设置超时时间为5秒
            while (!Application.isShutdownRequested()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Server.getServer().addClientAmount(1);
                    Logger.info(" ");
                    Logger.info("客户端IP" + clientSocket.getInetAddress() + " 已成功连接！");
                    Logger.info("当前已连接的客户端数量: " + Server.getServer().getClientAmount());

                    // 处理连接，分配线程给客户端连接后处理
                    threadPool.submit(new ClientHandler(clientSocket));

                } catch (SocketTimeoutException e) {
                    // 超时处理
                    //Logger.warning(e.getMessage());
                } catch (IOException e) {
                    Logger.error(e.getMessage());
                }
            }

        } catch (IOException e) {
            Logger.error(e.getMessage());
        } finally {
            try {
                serverSocket.close();
                threadPool.shutdown();
                Logger.info("已断开所有连接.");
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        }
    }


}
