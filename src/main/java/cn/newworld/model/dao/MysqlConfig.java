package cn.newworld.model.dao;

import cn.newworld.file.YamlConfiguration;
import cn.newworld.util.Logger;

public class MysqlConfig {
    private String databaseHost;
    private int databasePort;
    private String databaseName;
    private String userName;
    private String password;

    private static MysqlConfig instance;

    public static MysqlConfig getInstance(){
        if (instance == null)
            instance = new MysqlConfig();
        return instance;
    }

    /**
     * 加载读取mysql.yml数据
     */
    public void load(){
        try{
            YamlConfiguration yamlConfiguration = new YamlConfiguration("mysql.yml");
            this.databaseHost = yamlConfiguration.getString("database-host");
            this.databasePort = yamlConfiguration.getInt("database-port");
            this.databaseName = yamlConfiguration.getString("database-name");
            this.userName = yamlConfiguration.getString("username");
            this.password = yamlConfiguration.getString("password");
        }catch (Exception e){
            Logger.error(e.getMessage());
        }
    }

    /**
     * 获取数据库主机名或ip
     * @return 主机名或ip
     */
    public String getDatabaseHost(){
        return databaseHost;
    }

    /**
     * 获取数据库端口
     * @return 数据库端口
     */
    public int getDatabasePort(){
        return databasePort;
    }

    /**
     * 获取数据库名称
     * @return 返回数据库名称
     */
    public String getDatabaseName(){
        return databaseName;
    }

    /**
     * 获取用于数据库连接的用户名
     * @return 返回用户名
     */
    public String getUserName(){
        return userName;
    }

    /**
     * 获取用于数据库连接的密码
     * @return 密码
     */
    public String getPassword(){
        return password;
    }
}
