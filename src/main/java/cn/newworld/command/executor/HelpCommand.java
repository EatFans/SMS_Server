package cn.newworld.command.executor;

import cn.newworld.command.CommandExecutor;
import cn.newworld.util.Logger;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(String command, String[] args) {
        if (command.equalsIgnoreCase("help")){
            if (args.length == 1){
                Logger.info("==================|命令帮助|==================");
                Logger.info("- list 显示当前有多少台客户端连接.");
                Logger.info("- reload [参数] 重载...");
                Logger.info("- encrypt [需要加密的字符串] [盐]");
                Logger.info("- exit 关闭退出服务端.");
                Logger.info("- test 测试指令");
                return true;
            }

        }
        return false;
    }
}
