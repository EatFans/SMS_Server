/**
 * 这是一个用于储存服务器各种参数的数据模型，用于存放服务器端口、心跳间隔、最大连接等数据的数据模型类
 */
package cn.newworld.model;

import cn.newworld.io.YamlConfiguration;
import cn.newworld.util.Logger;

public class ServerConfig {
    private int port;
    private int threadPoolAmount;
    private int heartbeatInterval;
    private int timeout;
    private String keystorePassword;
    private static ServerConfig instance;

    /**
     * 获取Server类的实例
     * @return 返回实例对象
     */
    public static ServerConfig getInstance(){
        if (instance == null)
            instance = new ServerConfig();
        return instance;
    }

    /**
     * 该类用于读取加载server.yml文件中的数据进入Server数据对象模型中
     */
    public void load(){
        try {
            YamlConfiguration yamlConfiguration = new YamlConfiguration("server.yml");
            this.port = yamlConfiguration.getInt("server_port");
            this.threadPoolAmount = yamlConfiguration.getInt("thread_pool");
            this.heartbeatInterval = yamlConfiguration.getInt("heartbeat_interval");
            this.timeout = yamlConfiguration.getInt("timeout");
            this.keystorePassword = yamlConfiguration.getString("keystore_password");
        } catch (Exception e){
            Logger.error(e.getMessage());
        }
    }


    public int getPort(){
        return port;
    }


    public void setPort(int port){
        this.port = port;
    }

    public int getThreadPoolAmount(){
        return threadPoolAmount;
    }


    public void setThreadPoolAmount(int threadPoolAmount){
        this.threadPoolAmount = threadPoolAmount;
    }


    public int getHeartbeatInterval(){
        return heartbeatInterval;
    }


    public void setHeartbeatInterval(int heartbeatInterval){
        this.heartbeatInterval = heartbeatInterval;
    }

    public int getTimeout(){
        return timeout;
    }

    public String getKeystorePassword(){
        return keystorePassword;
    }
}
