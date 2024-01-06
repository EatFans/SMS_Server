/**
 * 程序初始化类
 */
package cn.newworld.controller;


import cn.newworld.file.ApplicationConfig;
import cn.newworld.file.FileManager;
import cn.newworld.model.Server;
import cn.newworld.util.Logger;

public class AppInitializer {
    /**
     * 初始化资源
     */
    public void initResource() {
        FileManager.extractFile("application.properties");
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        Logger.info(" ");
        Logger.info("系统："+ applicationConfig.getProperty("server-name"));
        Logger.info("版本："+ applicationConfig.getProperty("server-version"));
        Logger.info("语言："+ applicationConfig.getProperty("language"));
        Logger.info("开发者："+ applicationConfig.getProperty("author"));
        Logger.info("网址："+ applicationConfig.getProperty("website"));
        Logger.info(" ");
        Logger.info("等待加载中...");
        try {
            Thread.sleep(4500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        FileManager.extractFile("server.yml","/settings/");
        FileManager.extractFile("mysql.yml","/settings/");

    }

    /**
     * 初始化创建文件目录
     */
    public void initDirection(){
        String[] directoriesToCreate = {"data","logs","plugins"};
        FileManager.createDirectory(directoriesToCreate);
    }

    /**
     * 初始化数据
     */
    public void initData(){
        Server.getServer().load();

    }
}
