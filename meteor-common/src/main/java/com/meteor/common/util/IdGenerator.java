package com.meteor.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 唯一流水号生成工具类
 *
 * @author SuperMu
 * @time 2019-07-16
 */
public class IdGenerator {
    private static AtomicInteger id = new AtomicInteger(1);
    //时间yyyyMMddHHmmss
    private static final java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * @param prefix 流水号前缀
     */
    public static synchronized String next(String prefix) {
        //线程安全时间生成
        Date date = new Date();

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime now = date.toInstant().atZone(zoneId).toLocalDateTime();
        String time = now.format(formatter);
//        //时间yyyyMMddHHmmss
//        String time = DateTime.now().toString(DatePattern.PURE_DATETIME_FORMAT);
        //随机数
        StringBuffer sb = new StringBuffer(prefix).append(time).append("_").append(id.getAndIncrement());
        return sb.toString();

    }

    public static void main(String[] args) {
        String prefix = "O_";
        System.out.println(IdGenerator.next(prefix));

    }

    /**
     * 重置锁的ID
     * 定时调用每天凌晨重置一次
     */
    public static void resetId() {
        IdGenerator.id.set(1);
    }
}
