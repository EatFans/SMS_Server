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
            OutputStream outputStream = clientSocket.getOutputStream();

            while (flag){
                String readLine = reader.readLine();
                // TODO: 获取来自客户端的String数据

                // TODO: 解析String数据为Json数据对象

                // TODO: 将Json数据对象解析处理，传入数据处理器进行处理，判断是什么事件，并做出操作，并再最后返回对回应值

                // TODO: 将回应值写入发送给客户端
                if (readLine == null){
                    flag = false;
                }
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


    // 当客户端连接后，循环获取来自客户端发送的输入流inputStream
    // 然后将获取的数据通过json解析类进行解析数据，将解析完的数据对象，传递给默认的数据处理类，
    // 处理类通过识别这个json对象中的event标签的string字符串数据，通过switch识别事件类型
    // 再将详细的json对象传进来，将该事件需要的数值传入，通过eventsManager来触发该事件，
    // 最后通过事件的处理监听器去执行事件操作，

}
