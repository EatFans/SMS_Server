/**
 * Yaml配置文件类，本类用于读取、写入yml文件数据
 */
package cn.newworld.file;


import cn.newworld.util.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class YamlConfiguration {
    protected Map<String, Object> yamlConfigData;
    protected String filePath;
    private static YamlConfiguration instance;
    public YamlConfiguration(String filePath){
        this.filePath = filePath;
        load();
    }

    /**
     * 获取YamlConfiguration类的实例，单例设计
     * @param filePath 文件的目录
     * @return 返回实例
     */
    public static YamlConfiguration getYamlConfiguration(String filePath){
        if (instance == null)
            instance = new YamlConfiguration(filePath);
        return instance;
    }

    /**
     * 加载yml文件并读取里面的内容
     */
    protected void load(){
        try {
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(filePath);
            yamlConfigData = yaml.load(inputStream);
        } catch (FileNotFoundException e){
            Logger.error(e.getMessage());
        }
    }

    /**
     * 获取yml文件中的字符串数据，如果为空就直接返回字符串"null"
     * @param key 关键词
     * @return 返回字符串类型
     */
    public String getString(String key){
        if (yamlConfigData == null)
            return "null";
        Object value = yamlConfigData.get(key);
        return (value instanceof String) ? (String) value : "null";
    }

    /**
     * 获取yml文件中的整数数据
     * @param key 关键词
     * @return 返回整数数值
     */
    public int getInt(String key){
        if (yamlConfigData == null)
            return -1;
        Object value = yamlConfigData.get(key);
        return (value instanceof Integer) ? (int) value : 0;
    }

    /**
     * 获取yml文件中的布尔数值，如果为空就默认为false
     * @param key 关键词
     * @return 返回布尔数值
     */
    public boolean getBoolean(String key){
        if (yamlConfigData == null){
            return false;
        }
        Object value = yamlConfigData.get(key);
        return (value instanceof Boolean) ? (Boolean) value : false;
    }

    /**
     * 获取yml文件中List<String>数值，如果为空就返回null
     * @param key 关键词
     * @return 返回List<String>
     */
    public List<String> getStringList(String key){
        if (yamlConfigData == null)
            return null;
        Object value = yamlConfigData.get(key);
        return (value instanceof List) ? (List<String>) value : null;
    }


}
