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

    @Override
    public void run(){
        // 客户端连接后的逻辑
        try{

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String clientData;
            while (reader.readLine() != null){
                clientData = reader.readLine();


            }


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
