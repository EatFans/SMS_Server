package cn.newworld.io;

import cn.newworld.util.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class ResourceYamlConfiguration extends YamlConfiguration{

    private static ResourceYamlConfiguration instance;

    /**
     * 例如：路径config.yml 或者 lang/zh-CN.yml
     * @param resourcePath 文件路径
     */
    public ResourceYamlConfiguration(String resourcePath){
        super(resourcePath);
    }
    @Override
    protected void load() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                Yaml yaml = new Yaml();
                yamlConfigData = yaml.load(inputStream);
            }
        } catch (IOException e) {
            Logger.error("Failed to close input stream: " + e.getMessage());
        }
    }

    /**
     * 获取ResourceYamlConfiguration类的实例
     * @param resourcePath 文件路径
     * @return 类的实例
     */
    public static synchronized ResourceYamlConfiguration getResourceYamlConfiguration(String resourcePath) {
        if (instance == null)
            instance = new ResourceYamlConfiguration(resourcePath);
        return instance;
    }
}
