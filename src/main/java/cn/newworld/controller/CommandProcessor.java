/**
 * 这是指令系统的主类，用于处理输入的指令，并去执行对应指令要求
 *
 * @author EatFan
 * @data 2023/12/16
 */
package cn.newworld.controller;

import cn.newworld.Application;
import cn.newworld.command.CommandExecutor;
import cn.newworld.command.DefaultCommandExecutor;
import cn.newworld.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandProcessor implements Runnable {
    private final Map<String, CommandExecutor> commandExecutorMap;
    private DefaultCommandExecutor defaultCommandExecutor;

    /**
     * 构造方法，初始化指令控制器
     */
    public CommandProcessor(){
        this.commandExecutorMap = new HashMap<>();
        // 初始化默认指令监听
        this.defaultCommandExecutor = new DefaultCommandExecutor();
    }

    /**
     * 注册新的指令监听
     * @param Name 新指令监听名字
     * @param commandExecutor 新指令监听的实例对象
     */
    public void registerCommandExecutor(String Name,CommandExecutor commandExecutor){
        commandExecutorMap.put(Name.toLowerCase(),commandExecutor);
    }

    /**
     * 指令系统线程，重复循环输入指令处理指令操作行为.
     * 首先，获取用户输入的字符串，然后以空格为分隔符，将用户输入的指令分割成字符串数组形式存储
     * 然后，获取字符串数组的第一位转化成小写存储为字符串，在已经注册的指令监听中寻找对应的监听，如果没用找到就使用默认监听
     * 最后再通过指令监听，在其中执行指令，并返回true，true标识指令已执行.
     */
    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (!Application.isShutdownRequested()){
            String userInput = scanner.nextLine();
            String[] command = userInput.split(" ");

            String commandName = command[0].toLowerCase();
            CommandExecutor commandExecutor = commandExecutorMap.getOrDefault(commandName, defaultCommandExecutor);
            boolean commandFlag = commandExecutor.onCommand(command);
            if (!commandFlag)
                Logger.warning("指令未执行成功.");

            // 关闭Scanner资源，跳出循环

        }
        Logger.info("[ 命令模块 ] 已经关闭.");
        scanner.close();

    }



}
