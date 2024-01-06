package cn.newworld;

import cn.newworld.controller.CommandProcessor;
import cn.newworld.controller.AppInitializer;
import cn.newworld.controller.SocketConnectionHandler;
import cn.newworld.file.ResourceYamlConfiguration;
import cn.newworld.model.Server;
import cn.newworld.util.Logger;

import java.io.IOException;
import java.util.List;

public class Application {
    private static volatile boolean shutdownRequested = false; // 标志用于通知线程退出

    /**
     * 请求关闭线程
     */
    public static void requestShutdown() {
        shutdownRequested = true;
    }

    /**
     * 判断是否请求关闭线程
     * @return true表示请求关闭，false表示未请求关闭
     */
    public static boolean isShutdownRequested() {
        return shutdownRequested;
    }



    public static void main(String[] args) {
        // 初始化
        AppInitializer appInitializer = new AppInitializer();
        appInitializer.initDirection();
        Logger.init();
        appInitializer.initResource();
        appInitializer.initData();
        Runtime.getRuntime().addShutdownHook(new Thread(Logger::close)); // 注册自动关闭日志系统并保存日志信息
        Logger.info("[ 日志模块 ] 成功加载.");
        Logger.info("[ 命令模块 ] 成功加载.");
        Logger.info("[ 文件模块 ] 成功加载.");
        Logger.info("[ 心跳模块 ] 未开发...");
        int port = Server.getServer().getPort();
        Logger.info("服务端即将开放端口 "+port+" 用于连接...");


        try {
            // 连接监听线程
            SocketConnectionHandler socketConnectionHandler = new SocketConnectionHandler(port);
            Thread connectionHandlerThread = new Thread(socketConnectionHandler);
            connectionHandlerThread.start();
            // 指令监听线程
            CommandProcessor commandProcessor = new CommandProcessor();
            Thread commandProcessorThread = new Thread(commandProcessor);
            commandProcessorThread.start();

            // TODO: 加载插件补丁单独一个线程与主线程分开，对插件补丁进行加载并循环使用监听


            // 程序主循环
            while (!isShutdownRequested()) {
                // TODO: 主循环逻辑代码，用于对业务逻辑的处理


                if (shutdownRequested){
                    // 释放资源，保存数据
                    Logger.close();
                    break;
                }
                try {
                    Thread.sleep(1000); // 休眠1秒
                } catch (InterruptedException e) {
                    Logger.error(e.getMessage());
                }
            }
            // 释放线程
            connectionHandlerThread.join();
            commandProcessorThread.join();
        } catch (IOException | InterruptedException e){
            Logger.error(e.getMessage());
        }

    }

    /**
     * 用测试功能的方法
     */
    public static void test(){
        ResourceYamlConfiguration resourceYamlConfiguration = ResourceYamlConfiguration.getResourceYamlConfiguration("lang/zh-CN.yml");
        List<String> test = resourceYamlConfiguration.getStringList("test");
        for (String string : test)
            Logger.info(string);
    }
}