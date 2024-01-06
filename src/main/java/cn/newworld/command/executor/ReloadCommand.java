package cn.newworld.command.executor;

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
                Logger.info("未开发！");
                // TODO: 重载指令

            }
            if (args.length > 2){
                Logger.info("请检查用法格式！ \"reload [参数]\"");
            }
            return true;
        }
        return false;
    }
}
