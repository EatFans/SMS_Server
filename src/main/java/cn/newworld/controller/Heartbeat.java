/**
 * 该类是一个心跳机制类，里面有一些关于心跳机制的操作方法
 */
package cn.newworld.controller;

import java.net.Socket;

public class Heartbeat {
    private final Socket clientSocket;
    private final int heartbeatInterval;

    /**
     * 构造方法，初始化心跳系统
     * @param clientSocket 客户端的Socket套字节
     * @param heartbeatInterval 心跳间隔
     */
    public Heartbeat(Socket clientSocket,int heartbeatInterval){
        this.clientSocket = clientSocket;
        this.heartbeatInterval = heartbeatInterval;
    }

    /**
     * 向客户端发送心跳检查数据包
     */
    public void sendHeartbeat(){

    }

    /**
     * 获取客户端返回的心跳检查包
     * @return 如果返回false代表客户端已经断开连接，如果返回true表示客户端还存活
     */
    public boolean receiveHeartbeat(){
        return true;
    }
}
