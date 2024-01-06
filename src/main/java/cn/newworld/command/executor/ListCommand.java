package cn.newworld.command.executor;

import cn.newworld.command.CommandExecutor;
import cn.newworld.model.dao.Server;
import cn.newworld.util.Logger;

public class ListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(String command, String[] args) {
        if (command.equalsIgnoreCase("list")){
            if (args.length == 1){
                int clientAmount = Server.getServer().getClientAmount();
                Logger.info("- 当前有 "+clientAmount+" 台客户端正在连接中.");
                return true;
            }
        }
        return false;
    }
}
