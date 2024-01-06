/**
 * 这是一个用于储存服务器各种参数的数据模型，用于存放服务器端口、心跳间隔、最大连接等数据的数据模型类
 */
package cn.newworld.model.dao;

import cn.newworld.file.YamlConfiguration;
import cn.newworld.util.Logger;

public class Server {
    private int port;
    private int connectionMax;
    private int heartbeatInterval;
    private int clientAmount;
    private int timeout;
    private static Server instance;

    /**
     * 获取Server类的实例
     * @return 返回实例对象
     */
    public static Server getServer(){
        if (instance == null)
            instance = new Server();
        return instance;
    }

    /**
     * 该类用于读取加载server.yml文件中的数据进入Server数据对象模型中
     */
    public void load(){
        try {
            YamlConfiguration yamlConfiguration = new YamlConfiguration("server.yml");
            this.port = yamlConfiguration.getInt("server_port");
            this.connectionMax = yamlConfiguration.getInt("connection_max");
            this.heartbeatInterval = yamlConfiguration.getInt("heartbeat_interval");
            this.clientAmount = 0;
            this.timeout = yamlConfiguration.getInt("timeout");
        } catch (Exception e){
            Logger.error(e.getMessage());
        }
    }

    /**
     * 获取Server数据对象模型中的端口数据
     * @return 返回整数类型
     */
    public int getPort(){
        return port;
    }

    /**
     * 设置Server数据对象模型中的端口数据
     * @param port 整数类型端口
     */
    public void setPort(int port){
        this.port = port;
    }

    /**
     * 获取Server数据对象模型中的最大连接数据
     * @return 返回整数类型
     */
    public int getConnectionMax(){
        return connectionMax;
    }

    /**
     * 设置Server数据对象模型中的最大连接数据
     * @param connectionMax 整数类型
     */
    public void setConnectionMax(int connectionMax){
        this.connectionMax = connectionMax;
    }

    /**
     * 获取Server数据对象模型中的心跳间隔数据
     * @return 返回整数类型（单位：毫秒）
     */
    public int getHeartbeatInterval(){
        return heartbeatInterval;
    }

    /**
     * 设置Server数据对象模型中心跳间隔数值
     * @param heartbeatInterval 整数类型
     */
    public void setHeartbeatInterval(int heartbeatInterval){
        this.heartbeatInterval = heartbeatInterval;
    }

    /**
     * 获取已经连接的客户端数量
     * @return 返回客户端数量
     */
    public int getClientAmount(){
        return clientAmount;
    }

    /**
     * 设置已经连接的客户端数量
     * @param clientAmount 已经连接的客户端数量
     */
    public void setClientAmount(int clientAmount){
        this.clientAmount = clientAmount;
    }

    /**
     * 添加已经连接的客户端数量
     * @param amount 被加的数量
     */
    public void addClientAmount(int amount){
        this.clientAmount = this.clientAmount + amount;
    }

    /**
     * 减少已经连接的客户端的数量
     * @param amount 被减去的数量
     */
    public void reduceClientAmount(int amount){
        this.clientAmount = this.clientAmount - amount;
    }

    /**
     * 获取连接超时时间
     * @return
     */
    public int getTimeout(){
        return timeout;
    }
}
