/**
 * IP白名单数据模型对象类
 */
package cn.newworld.model;

import cn.newworld.file.YamlConfiguration;
import cn.newworld.util.Logger;

import java.util.List;

public class Whitelist {
    private List<String> whitelist;
    private static Whitelist instance;
    public static Whitelist getInstance(){
        if (instance == null){
            instance = new Whitelist();
        }
        return instance;
    }

    /**
     * 加载IP白名单数据
     */
    public void load(){
        try {
            YamlConfiguration yamlConfiguration = new YamlConfiguration("whitelist.yml");
            this.whitelist = yamlConfiguration.getStringList("whitelist");
        } catch (Exception e){
            Logger.error(e.getMessage());
        }
    }

    public List<String> getWhitelist(){
        return whitelist;
    }

    public void addWhiteList(String clientIP){
        whitelist.add(clientIP);
    }

    public void remoteWhiteList(String clientIP){
        whitelist.remove(clientIP);
    }
}
