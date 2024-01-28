/**
 * 服务器配置文件类，该类用于读取application.properties配置文件，并保存调用数据
 * @author EatFan
 */
package cn.newworld.file;

import cn.newworld.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ApplicationConfig {
    private static ApplicationConfig instance;
    private static final String CONFIG_FILE = "application.properties";
    private Properties properties;
    private final Path applicationConfigPath = Paths.get(CONFIG_FILE);
    public ApplicationConfig(){
        properties = new Properties();
        loadServerConfig();
    }

    /**
     * 加载读取application.properties配置文件
     */
    private void loadServerConfig(){
        if (Files.exists(applicationConfigPath)){
            try (InputStream inputStream = Files.newInputStream(applicationConfigPath)){
                properties.load(inputStream);
            } catch (IOException e){
                Logger.error(e.getMessage());
            }
        }
    }

    /**
     * 读取该配置文件中的Property数值
     * @param key 标识符
     * @return String数值
     */
    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public int getIntProperty(String key){
        return Integer.parseInt(properties.getProperty(key));
    }

    /**
     * 获取ServerConfig实例对象
     * @return ServerConfig实例对象
     */
    public static ApplicationConfig getInstance(){
        if (instance == null)
            instance = new ApplicationConfig();
        return instance;
    }

}
