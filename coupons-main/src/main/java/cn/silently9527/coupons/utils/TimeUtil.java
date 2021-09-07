package cn.silently9527.coupons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 时间工具类
 *
 * @author starBlues
 * @version 1.0
 */
public class TimeUtil {

    public static final String FORMAT_T_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String FORMAT_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_SECONDS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DAY = "yyyy年MM月dd日";


    private TimeUtil(){}

    /**
     * 获取现在的时间戳
     * @return
     */
    public static Long getNowTimeStamp(){
        Instant instant = Instant.now();
        return instant.toEpochMilli();
    }

    /**
     * 获得现在的时间，精确到秒，格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTimeToDay(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_DAY);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获得现在的时间，精确到秒，格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTimeToSeconds(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_SECONDS);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获得现在的时间，精确到毫秒，格式为yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static String getNowTimeToMs(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_MS);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获得现在的时间，通过指定的格式
     * @param format 时间格式
     * @return
     */
    public static String getNowTimeByFormat(String format){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String formatTime(long time){
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_SECONDS);
        return dateTimeFormatter.format(localDateTime);
    }


    /**
     * 格式化字符串类型的时间，
     * @param time 字符串时间
     * @param format 时间格式
     * @return
     */
    public static String formatTime(String time, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 格式化Date类型事件
     * @param date
     * @param format
     * @return
     */
    public static String formatTime(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式化Date类型事件
     * @param date
     * @return
     */
    public static String formatTimeMs(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_MS);
        return sdf.format(date);
    }

    /**
     *  yyyy-MM-dd HH:mm:ss.SSS 字符串时间转Date
     * @return
     */
    public static Date getDateByStringMs(String date) throws ParseException {
        return getDateByString(date, FORMAT_MS);
    }

    /**
     * 自定义格式时间字符串转Date
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date getDateByString(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS 格式 时间字符串转时间戳
     * @param date
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String date) throws ParseException{
        return dateToStamp(date, FORMAT_MS);
    }

    /**
     * 自定义格式 时间字符串转时间戳
     * @param date
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String date, String format) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date).getTime();
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS 字符串时间转LocalDateTime
     * @param time
     * @return
     */
    public static LocalDateTime parseStringToLocalDateTime(String time) {
        return parseStringToLocalDateTime(time, FORMAT_MS);
    }

    /**
     * 自定义格式 时间字符串转换成LocalDateTime
     * @param time
     * @param format
     * @return
     */
    public static LocalDateTime parseStringToLocalDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * LocalDateTime转为 yyyy-MM-dd HH:mm:ss.SSS 时间字符串
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime) {
        return formatLocalDateTimeToString(localDateTime, FORMAT_MS);
    }

    /**
     * LocalDateTime转为自定义的时间格式的字符串
     * @param localDateTime
     * @param format
     * @return
     */
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS 时间字符串 获取其几分钟前的时间 yyyy-MM-dd HH:mm:ss.SSS 字符串
     * @param time
     * @param minutes
     * @return
     */
    public static String getMinusMSMinutes(String time, long minutes) {
        return getMinusMinutes(time, minutes, FORMAT_MS);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 时间字符串 获取其几分钟前的时间 yyyy-MM-dd HH:mm:ss 字符串
     * @param time
     * @param minutes
     * @return
     */
    public static String getMinusSEMinutes(String time, long minutes) {
        return getMinusMinutes(time, minutes, FORMAT_SECONDS);
    }

    /**
     * 自定义 时间字符串  获取其几分钟前的时间 ，以原型式输出
     * @param time
     * @param minutes
     * @param format
     * @return
     */
    public static String getMinusMinutes(String time, long minutes, String format) {
        LocalDateTime localDateTime = parseStringToLocalDateTime(time, format);
        LocalDateTime minusLocalDateTime = localDateTime.minusMinutes(minutes);
        return formatLocalDateTimeToString(minusLocalDateTime, format);
    }

    /**
     * 根据传入的日期格式，获取其几分钟前的时间 ，以原型式输出
     * @param time
     * @param minutes
     * @return
     */
    public static String getMinusMinutes(String time, long minutes) {
        String[] times = time.split(" ");
        if(times.length < 2) {
            // 年月日
            return getMinusMinutes(time, minutes, "yyyy-MM-dd");
        }else if(times[1].contains(".")) {
            // 精确到毫秒
            return getMinusMSMinutes(time, minutes);
        }
        // 精确到秒
        return getMinusSEMinutes(time, minutes);
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS 时间字符串 获取其几分钟后的时间 yyyy-MM-dd HH:mm:ss.SSS 字符串
     * @param time
     * @param minutes
     * @return
     */
    public static String getPlusMSMinutes(String time, long minutes) {
        return getPlusMinutes(time, minutes, FORMAT_MS);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 时间字符串 获取其几分钟后的时间 yyyy-MM-dd HH:mm:ss 字符串
     * @param time
     * @param minutes
     * @return
     */
    public static String getPlusSEMinutes(String time, long minutes) {
        return getPlusMinutes(time, minutes, FORMAT_SECONDS);
    }

    /**
     * 自定义 时间字符串  获取其几分钟后的时间 ，以原型式输出
     * @param time
     * @param minutes
     * @param format
     * @return
     */
    public static String getPlusMinutes(String time, long minutes, String format) {
        LocalDateTime localDateTime = parseStringToLocalDateTime(time, format);
        LocalDateTime minusLocalDateTime = localDateTime.plusMinutes(minutes);
        return formatLocalDateTimeToString(minusLocalDateTime, format);
    }

    /**
     * 根据传入的日期格式，获取其几分钟后的时间 ，以原型式输出
     * @param time
     * @param minutes
     * @return
     */
    public static String getPlusMinutes(String time, long minutes) {
        String[] times = time.split(" ");
        if(times.length < 2) {
            // 年月日
            return getPlusMinutes(time, minutes, "yyyy-MM-dd");
        }else if(times[1].contains(".")) {
            // 精确到毫秒
            return getPlusMSMinutes(time, minutes);
        }
        // 精确到秒
        return getPlusSEMinutes(time, minutes);
    }


    /**
     * 自定义格式 时间字符串转换成LocalDate
     * @param time
     * @param format
     * @return
     */
    public static LocalDate parseStringToLocalDate(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(time, df);
    }


    /**
     * LocalDate转为 yyyy-MM-dd 时间字符串
     * @param localDate
     * @return
     */
    public static String formatLocalDateToString(LocalDate localDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }

    /**
     * 比较2个时间大小，前者大返回true
     * @param time1
     * @param time2
     * @return
     */
    public static Boolean time1LgTime2(String time1, String time2) {
        LocalDateTime localDateTime1 = turnLocalDateTime(time1);
        LocalDateTime localDateTime2 = turnLocalDateTime(time2);
        return localDateTime1.isAfter(localDateTime2);
    }

    public static LocalDateTime turnLocalDateTime(String time) {
        String[] times = time.split(" ");
        if(times.length < 2) {
            // 年月日
            return parseStringToLocalDateTime(time+" 00:00:00", FORMAT_SECONDS);
        }
        if(times[1].contains(".")) {
            // 毫秒
            return parseStringToLocalDateTime(time, FORMAT_MS);
        }
        String[] date = times[1].split(":");
        if(date.length > 3) {
            // 毫秒
            return parseStringToLocalDateTime(time, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        return parseStringToLocalDateTime(time, FORMAT_SECONDS);
    }

    public static String formatTime(String date, String sourceFormat, String targetFormat){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourceFormat);
            Date d = simpleDateFormat.parse(date);
            return formatTime(d,targetFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 返回两个时间字符串之间的秒
     */
    public static float getSecondsBetweenFirstTimeAndLastTime(String firstTime, String lastTime,String format){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            final Date parse = simpleDateFormat.parse(firstTime);
            final Date parse1 = simpleDateFormat.parse(lastTime);
            return (parse1.getTime() - parse.getTime())/1000.0f;
        } catch (ParseException e){
            e.printStackTrace();
            return 0f;
        }


    }

    /**
     * 将给定的毫秒值转成时分秒格式
     * @param l
     * @return
     */
    public static String getUnStandardTimeByMs(long l){
        if(l<0){
            // 是以24：00为分界线，之前记为负数，之后记为正数，因返回只有时分秒，所以加上一天避免负数无法计算
            l = l + 86400000L;
        }
        String h = String.valueOf((l%(24*60*60*1000))/(60*60*1000));
        String M = String.valueOf((l%(60*60*1000))/(60*1000));
        String s = String.valueOf((l%(60*1000))/1000);
        return h+":"+M+":"+s;
    }

}
