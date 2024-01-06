package cn.newworld.command;

import cn.newworld.Application;
import cn.newworld.model.dao.Server;
import cn.newworld.util.EncryptionUtils;
import cn.newworld.util.Logger;


public class DefaultCommandExecutor implements CommandExecutor{
    @Override
    public boolean onCommand(String[] command) {
        if (command[0].equalsIgnoreCase("list")){
            int clientAmount = Server.getServer().getClientAmount();
            Logger.info("- 当前有 "+clientAmount+" 台客户端正在连接中.");
            return true;
        }

        if (command[0].equalsIgnoreCase("exit")){
            Logger.info("正在关闭服务器中...");
            Application.requestShutdown();
            return true;
        }

        if (command[0].equalsIgnoreCase("reload")){
            if (command.length == 1){
                Logger.info("请重新确定命名格式\"reload [参数] \"");
            }
            if (command.length > 1 && command[1].equalsIgnoreCase("config")){
                Logger.info("正在重载配置文件中...");
                // TODO: 重新加载配置文件
                Server.getServer().load();

                Logger.info("配置文件数据重载成功！");

            }
            return true;
        }

        if (command[0].equalsIgnoreCase("encrypt")){
            if (command.length <= 2)
                Logger.info("请检查encrypt命令语法，\"encrypt [需要加密的字符串] [盐]\"");
            if (command.length == 3){
                String encryptedString = EncryptionUtils.hashWithSalt(command[1], command[2]);
                Logger.info(" ");
                Logger.info("加密完成！");
                Logger.info(" ");
                Logger.info("- "+encryptedString);
                return true;
            }
        }

        if (command[0].equalsIgnoreCase("help")){
            Logger.info("==================|命令帮助|==================");
            Logger.info("- list 显示当前有多少台客户端连接.");
            Logger.info("- reload [参数] 重载...");
            Logger.info("- encrypt [需要加密的字符串] [盐]");
            Logger.info("- exit 关闭退出服务端.");
            Logger.info("- test 测试指令");
            return true;
        }
        // 测试指令
        if (command[0].equalsIgnoreCase("test")){
            int port = Server.getServer().getPort();
            int connectionMax = Server.getServer().getConnectionMax();
            int heartbeatInterval = Server.getServer().getHeartbeatInterval();
            Logger.info("端口："+port+" 最大连接数："+connectionMax+" 心跳间隔: "+heartbeatInterval);
            return true;
        }
        return false;
    }

}
