/**
 * 本类是用于对命令管理的一个类，用于注册指令等操作
 */
package cn.newworld.command;

import cn.newworld.command.CommandExecutor;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, CommandExecutor> commandExecutorMap = new HashMap<>();



    /**
     * 注册命令
     * @param commandName 主命令的名称
     * @param commandExecutor 命令执行器的类对象，要继承CommandExecutor接口
     */
    public static void registerCommandExecutor(String commandName,CommandExecutor commandExecutor){
        commandExecutorMap.put(commandName,commandExecutor);
    }

    public static Map<String, CommandExecutor> getCommandExecutor(){
        return commandExecutorMap;
    }

    public static void close(){
        commandExecutorMap.clear();
    }

}
