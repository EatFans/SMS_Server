/**
 * 该类是关于 exit 命令的执行器，用于对 exit 命令的功能实现
 */
package cn.newworld.command.executor;

import cn.newworld.Server;
import cn.newworld.command.CommandExecutor;
import cn.newworld.util.Logger;

public class ExitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(String command, String[] args) {
        if (command.equalsIgnoreCase("exit")){
            if (args.length == 1){
                Logger.info("服务端正在关闭中...");
                Server.requestShutdown();
                return true;
            }

        }
        return false;
    }
}
