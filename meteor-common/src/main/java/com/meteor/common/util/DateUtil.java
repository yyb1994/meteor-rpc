package com.meteor.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SuperMu
 * @time 2019-11-25
 */
public class DateUtil {
    private static final Map<String, Byte> weekMap;

    static {
        weekMap = new ConcurrentHashMap<String, Byte>();
        weekMap.put("MONDAY", (byte) 1);
        weekMap.put("TUESDAY", (byte) 2);
        weekMap.put("WEDNESDAY", (byte) 3);
        weekMap.put("THURSDAY", (byte) 4);
        weekMap.put("FRIDAY", (byte) 5);
        weekMap.put("SATURDAY", (byte) 6);
        weekMap.put("SUNDAY", (byte) 7);
    }

    /**
     * 获取当前时间是星期几
     **/
    public static Byte getDayOfTheWeek() {
        LocalDate currentDate = LocalDate.now();
        String k = String.valueOf(currentDate.getDayOfWeek());
        return weekMap.get(k);
    }

    public static void main(String[] args) {
        System.out.println(getDayOfTheWeek() > (byte) 1);
        LocalTime nowTime = LocalTime.now().withNano(0);
        LocalTime strDateBeginTime = LocalTime.now().plusHours(-2);
        LocalTime strDateEndTime = LocalTime.now().plusHours(2);

        System.out.println(nowTime.isAfter(strDateBeginTime) && nowTime.isBefore(strDateEndTime));


        //获取当前时间  含有毫秒值  17:18:41.571
        LocalTime now = LocalTime.now();
        System.out.println(now);

        //获取当前时间   去掉毫秒值   17:45:41
        LocalTime now1 = LocalTime.now().withNano(0);
        System.out.println(now1);
        System.out.println(now1.toString());
        //00:46:46.651  提供了把时分秒都设为0的方法
        LocalTime now2 = LocalTime.now().withHour(0);
        System.out.println(now2);

        //构造时间  00:20:55
        LocalTime time1 = LocalTime.of(0, 20, 55);
        System.out.println(time1);
        //构造时间  05:43:22
        LocalTime time2 = LocalTime.parse("05:43:22");
        System.out.println(time2);


        //标准时间 2017-11-06T17:53:15.930
        LocalDateTime lt = LocalDateTime.now();
        System.out.println(lt);
        //https://www.yiibai.com/javatime/javatime_localtime.html

    }

    public static boolean isTime(Class tClass) {
        return tClass == Date.class || tClass == LocalDate.class || tClass == LocalDateTime.class;
    }

    //java.util.Date --> java.time.LocalDate
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localDateTimeDate2Date(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalTime date2LocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }

    public static Date localTime2Date(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * string类型转化为 date类型
     */
    public static Date strToDate(String strdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(strdate);
        } catch (ParseException e) {
            strdate += " 00:00:00";
            date = DateUtil.strToDate(strdate);
        }
        return date;
    }

    /**
     * 判断当前时间是否在两个时间之内
     *
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param compareTime 比较时间
     * @return 比较结果
     */
    public static boolean isInLocalTime(LocalTime startTime, LocalTime endTime, LocalTime compareTime) {
        return compareTime.isBefore(endTime) && compareTime.isAfter(startTime);
    }

    public static boolean isInWeek(byte start, byte end, byte compare) {
        return compare >= start && compare <= end;
    }


}
