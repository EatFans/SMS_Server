/**
 * 日志类，该类是日志系统类，用于日志输入并当每次关闭程序时候将所有输入的日志信息保存到.log文件中放置到logs目录下
 */
package cn.newworld.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
    private static PrintWriter logWriter;

    /**
     * 初始化日志系统
     */
    public static void init(){
        String logFileName = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss").format(new Date())+".log";
        File logFile = new File("logs", logFileName);
        try {
            logWriter = new PrintWriter(new FileWriter(logFile,true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 关闭日志系统
     */
    public static void close(){
        if (logWriter != null){
            logWriter.flush();
            logWriter.close();
        }
    }

    /**
     * 获取系统时间并保存格式返回
     * @return 返回已经设定好的时间格式
     */
    private static String getTimeFormat(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    public static void info(String message){
        String outputMessage = "["+getTimeFormat()+" INFO] "+message;
        logWriter.println(outputMessage);
        System.out.println(outputMessage);
    }

    public static void warning(String message){
        String outputMessage = "["+getTimeFormat()+" WARN] "+message;
        logWriter.println(outputMessage);
        System.out.println(outputMessage);
    }

    public static void error(String message){
        String outputMessage = "["+getTimeFormat()+" ERROR] "+message;
        logWriter.println(outputMessage);
        System.out.println(outputMessage);
    }


}
