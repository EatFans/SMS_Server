/**
 * 该类用于对客户端的监听处理
 *
 */
package cn.newworld.controller;


import cn.newworld.model.dao.ServerConfig;
import cn.newworld.util.Logger;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private boolean flag;
    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        this.flag = true;
    }

    /**
     * 当客户端连接到服务端后，初始化一个心跳监听系统，并获取来自客户端输入流，
     * 当客户端连接到服务端后，客户端先向服务端发送一段加密的密钥给服务端，
     * 服务端接收到这个加密的密钥，验证密钥是否匹配，如果不匹配就直接断开该客户端的连接，
     * 如果密钥匹配上了，就继续执行逻辑，启动一个处理循环；
     * 在这个循环中，心跳系统持续向客户端发送数据包，如果客户端接收到这段心跳数据包，
     * 就向服务端反应客户端还存活，服务端继续持续接收以及发送客户端的数据，如果服务端向客户端发送数据包，
     * 客户端没有向服务端做出反应，则服务端判断该客户端已经断开连接，立即释放该客户端运行的线程以及其他资源。
     *
     */
    @Override
    public void run(){
        // 客户端连接后的逻辑
        try{
            // 初始化心跳系统
            Heartbeat heartbeat = new Heartbeat(clientSocket, ServerConfig.getInstance().getHeartbeatInterval());


            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            do {
                Logger.info("密钥验证通过，已经成功连接...");
                heartbeat.sendHeartbeat();  // 发送心跳检查数据包
                this.flag = heartbeat.receiveHeartbeat();  // 获取心跳检查数据包反应

            } while (flag);


        } catch (IOException e){
            Logger.warning(e.getMessage());
        } finally {
            try {
                Logger.info("客户端 " + clientSocket.getInetAddress() + " 已经断开连接...");
                ServerConfig.getInstance().reduceClientAmount(1);
                clientSocket.close();
            } catch (IOException e){
                Logger.warning(e.getMessage());
            }
        }

    }

}
