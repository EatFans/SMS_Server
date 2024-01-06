package cn.newworld.command;

public interface CommandExecutor {
    /**
     * 命令实现执行的接口方法
     * @param command 命令的主名称
     * @param args 命令的String数组
     * @return 如果返回false就表示指令执行失败，返回true表示指令执行成功
     */
    boolean onCommand(String command,String[] args);
}
