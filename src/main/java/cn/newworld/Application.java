package cn.newworld;

import cn.newworld.command.CommandExecutor;
import cn.newworld.command.CommandManager;
import cn.newworld.command.executor.ExitCommand;
import cn.newworld.command.executor.HelpCommand;
import cn.newworld.command.executor.ListCommand;
import cn.newworld.command.executor.ReloadCommand;
import cn.newworld.controller.SocketConnectionHandler;
import cn.newworld.file.ApplicationConfig;
import cn.newworld.file.FileManager;
import cn.newworld.file.ResourceYamlConfiguration;
import cn.newworld.model.dao.Server;
import cn.newworld.util.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    private static volatile boolean shutdownRequested = false; // 标志用于通知线程退出

    private static Scanner scanner;

    private static CommandManager commandManager;

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

    /**
     * 初始化资源
     */
    public static void initResource(){
        FileManager.extractFile("application.properties");
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        Logger.info("------------------------------------------------------------------");
        Logger.info(" ");
        Logger.info("系统："+ applicationConfig.getProperty("server-name"));
        Logger.info("版本："+ applicationConfig.getProperty("server-version"));
        Logger.info("语言："+ applicationConfig.getProperty("language"));
        Logger.info("开发者："+ applicationConfig.getProperty("author"));
        Logger.info("网站："+ applicationConfig.getProperty("website"));
        Logger.info(" ");
        Logger.info("------------------------------------------------------------------");
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
    public static void initDirection(){
        String[] directoriesToCreate = {"data","logs","plugins"};
        FileManager.createDirectory(directoriesToCreate);
    }

    /**
     * 初始化对象、加载数据
     */
    public static void initData(){
        scanner = new Scanner(System.in);
        commandManager = new CommandManager();
        Server.getServer().load();
    }

    /**
     * 注册命令
     */
    public static void initCommand(){
        commandManager.registerCommandExecutor("exit",new ExitCommand());
        commandManager.registerCommandExecutor("help",new HelpCommand());
        commandManager.registerCommandExecutor("list",new ListCommand());
        commandManager.registerCommandExecutor("reload",new ReloadCommand());
        Logger.info("默认命令全部注册成功！");
    }

    /**
     * 注册事件
     */
    public static void initEventExecutor(){

        Logger.info("默认事件全部注册成功！");
    }

    public static void main(String[] args) {
        // 初始化
        initDirection();
        Logger.init();
        Runtime.getRuntime().addShutdownHook(new Thread(Logger::close)); // 注册自动关闭日志系统并保存日志信息
        initResource();
        initData();

        int port = Server.getServer().getPort();
        Logger.info("服务端即将开放端口 "+port+" 用于连接...");

        initCommand();
        initEventExecutor();



        try {
            // 连接监听线程
            SocketConnectionHandler socketConnectionHandler = new SocketConnectionHandler(port);
            Thread connectionHandlerThread = new Thread(socketConnectionHandler);
            connectionHandlerThread.start();

            // TODO: 加载插件补丁单独一个线程与主线程分开，对插件补丁进行加载并循环使用监听

            // TODO: 主循环逻辑代码，命令模块、指令监听模块
            while (!isShutdownRequested()) {
                String userInput = scanner.nextLine();
                String[] command = userInput.split(" ");
                String commandName = command[0].toLowerCase();
                Map<String, CommandExecutor> commandExecutorMap = commandManager.getCommandExecutor();
                CommandExecutor commandExecutor = commandManager.
                        getCommandExecutor().
                        getOrDefault(commandName,commandExecutorMap.get(commandName));
                if (commandExecutor != null){
                    if (!commandExecutor.onCommand(commandName,command))
                        Logger.warning("命令未执行成功！");
                } else {
                    Logger.warning("未注册 "+ commandName+ " 命令! 请检查后重试！");
                }



                if (shutdownRequested)
                    break;

            }
            // 释放资源，保存数据
            Logger.info("[ 命令模块 ] 已经关闭.");
            Logger.info("[ 日志模块 ] 已经关闭.");
            Logger.close();
            scanner.close();

            // 释放线程
            connectionHandlerThread.join();
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