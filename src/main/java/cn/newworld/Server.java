package cn.newworld;

import cn.newworld.command.CommandExecutor;
import cn.newworld.command.CommandManager;
import cn.newworld.command.executor.ExitCommand;
import cn.newworld.command.executor.HelpCommand;
import cn.newworld.command.executor.ReloadCommand;
import cn.newworld.command.executor.WhitelistCommand;
import cn.newworld.controller.processor.v1.OtherProcessor;
import cn.newworld.controller.processor.v1.TestProcessor;
import cn.newworld.core.ConnectHandler;
import cn.newworld.controller.ProcessorManager;
import cn.newworld.controller.processor.v1.AuthProcessor;
import cn.newworld.event.EventsManager;
import cn.newworld.file.ApplicationConfig;
import cn.newworld.file.FileManager;
import cn.newworld.model.MysqlConfig;
import cn.newworld.model.ServerConfig;
import cn.newworld.model.Whitelist;
import cn.newworld.util.Logger;
import cn.newworld.util.ThreadList;


import java.util.Map;
import java.util.Scanner;


public class Server {
    private  static ThreadList threadList;
    private static volatile boolean shutdownRequested = false; // 标志用于通知线程退出

    private static Scanner scanner;

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
        Logger.info(" _   _              __        __         _     _ ____  _             _ _       ");
        Logger.info("| \\ | | _____      _\\ \\      / /__  _ __| | __| / ___|| |_ _   _  __| (_) ___  ");
        Logger.info("|  \\| |/ _ \\ \\ /\\ / /\\ \\ /\\ / / _ \\| '__| |/ _` \\___ \\| __| | | |/ _` | |/ _ \\ ");
        Logger.info("| |\\  |  __/\\ V  V /  \\ V  V / (_) | |  | | (_| |___) | |_| |_| | (_| | | (_) |");
        Logger.info("|_| \\_|\\___| \\_/\\_/    \\_/\\_/ \\___/|_|  |_|\\__,_|____/ \\__|\\__,_|\\__,_|_|\\___/ ");
        Logger.info("                                                                               ");
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
        FileManager.extractFile("whitelist.yml","/settings/");
        FileManager.extractFile("Start.exe","/bin/");
    }

    /**
     * 初始化创建文件目录
     */
    private static void initDirection(){
        String[] directoriesToCreate = {"data","logs","plugins"};
        FileManager.createDirectory(directoriesToCreate);
    }
    /**
     * 初始化对象、加载数据
     */
    private static void initData(){
        scanner = new Scanner(System.in);
        threadList = new ThreadList();
        loadConfig();
        Logger.info("数据初始化完毕.");
    }

    /**
     * 注册命令
     */
    private static void initCommand(){
        CommandManager.registerCommandExecutor("exit",new ExitCommand());
        CommandManager.registerCommandExecutor("help",new HelpCommand());
        CommandManager.registerCommandExecutor("reload",new ReloadCommand());
        CommandManager.registerCommandExecutor("whitelist",new WhitelistCommand());
        Logger.info("默认命令全部注册成功！");
    }

    /**
     * 注册事件
     */
    private static void initEventExecutor(){

        Logger.info("默认事件全部注册成功！");
    }

    private static void initProcessor(){
        ProcessorManager.registerProcessor(new AuthProcessor());
        ProcessorManager.registerProcessor(new TestProcessor());
        ProcessorManager.registerProcessor(new OtherProcessor());
        Logger.info("默认请求处理器全部注册完成！");
    }

    /**
     * 加载插件
     */
    private  static void loadPlugin(){

        Logger.info("插件加载完毕.");
    }

    /**
     * 主循环中命令的处理
     */
    private static void processCommand(){
        String userInput = scanner.nextLine();
        String[] command = userInput.split(" ");
        if (command.length < 1){
            return;
        }
        String commandName = command[0].toLowerCase();
        Map<String, CommandExecutor> commandExecutorMap = CommandManager.getCommandExecutor();
        CommandExecutor commandExecutor = CommandManager.
                getCommandExecutor().
                getOrDefault(commandName,commandExecutorMap.get(commandName));
        if (commandExecutor != null){
            if (!commandExecutor.onCommand(commandName,command))
                Logger.warning("命令未执行成功！");
        } else {
            Logger.warning("未注册 "+ commandName+ " 命令! 请检查后重试！");
        }
    }


    /**
     * 服务器关闭逻辑处理
     */
    private static void closeServer(){
        // 释放资源，保存数据
        Logger.info("[ 命令模块 ] 已经关闭.");
        Logger.info("[ 日志模块 ] 已经关闭.");
        Logger.info("[ 事件模块 ] 已经关闭.");

        // 关闭线程
        ConnectHandler.requestShutdown();
        threadList.closeAllThread();

        scanner.close();
        CommandManager.close();
        EventsManager.close();
        ProcessorManager.close();
        Logger.close();
    }

    /**
     * 加载配置文件数据
     */
    public static void loadConfig(){
        ServerConfig.getInstance().load();
        MysqlConfig.getInstance().load();
        Whitelist.getInstance().load();
    }


    public static void main(String[] args) {
        // 初始化
        initDirection();
        Logger.init();
        Runtime.getRuntime().addShutdownHook(new Thread(Logger::close)); // 注册自动关闭日志系统并保存日志信息
        initResource();
        initData();
        initCommand();
        initEventExecutor();
        initProcessor();


        // TODO: 加载插件补丁，插件补丁用于各种事件指令重写，等等操作
        loadPlugin();

        threadList.addRunnable(new ConnectHandler());
        threadList.startAllThread();
        // TODO: 主循环逻辑代码，命令模块、事件监听模块
        while (!isShutdownRequested()) {

            processCommand();

            if (shutdownRequested)
                break;
        }
        closeServer();

    }

}