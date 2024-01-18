package cn.newworld.command.executor;

import cn.newworld.Server;
import cn.newworld.command.CommandExecutor;
import cn.newworld.util.Logger;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(String command, String[] args) {
        if (command.equalsIgnoreCase("reload")){
            if (args.length == 1){
                Logger.info("请检查用法格式！ \"reload [参数]\"");
            }
            if (args.length == 2){
                if (args[1].equals("config")){
                    Server.loadConfig();
                    Logger.info("配置文件已经重载完毕！");
                    return true;
                }
                Logger.warning("错误参数！");
            }
            if (args.length > 2){
                Logger.info("请检查用法格式！ \"reload [参数]\"");
            }
            return true;
        }
        return false;
    }
}
