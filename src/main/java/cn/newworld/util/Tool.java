package cn.newworld.util;

import java.time.Instant;

public class Tool {
    /**
     * 获取当前时间戳
     * @return 毫秒级
     */
    public static long getTimestamp(){
        return Instant.now().toEpochMilli();
    }
}
