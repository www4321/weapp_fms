package com.bupt.common;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static final SimpleDateFormat SECOND_WITH_DASH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat SECOND_WITHOUT_DASH = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final SimpleDateFormat DAY_WITH_DASH_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DAY_WITHOUT_DASH_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 根据格式转换为Date
     * 
     * @param time
     * @param sdf
     * @return
     * @throws ParseException
     */
    public static Date convert(String time, SimpleDateFormat sdf) throws ParseException {
        if (StringUtils.isBlank(time)) {
            return defaultDate();
        }
        return sdf.parse(time);
    }

    /**
     * 根据格式转换为String
     * 
     * @param date
     * @param sdf
     * @return
     */
    public static String convert(Date date, SimpleDateFormat sdf) {
        if (date == null) {
            if (sdf == SECOND_WITH_DASH) {
                return "1970-01-01 00:00:00";
            } else if (sdf == DAY_WITH_DASH_FORMAT) {
                return "1970-01-01";
            } else if (sdf == SECOND_WITHOUT_DASH) {
                return "19700101000000";
            } else if (sdf == DAY_WITHOUT_DASH_FORMAT) {
                return "19700101";
            }
            return "19700101";
        }
        return sdf.format(date);
    }

    /**
     * 从yyyy-MM-dd的String类型转化为Date
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date convertFromDayWithDash(String time) throws ParseException {
        return convert(time, DAY_WITH_DASH_FORMAT);
    }

    /**
     * 从yyyyMMdd的String类型转化为Date
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date convertFromDayWithoutDash(String time) throws ParseException {
        return convert(time, DAY_WITHOUT_DASH_FORMAT);
    }

    /**
     * 从yyyy-MM-dd HH:mm:ss的String类型转化为Date
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date convertFromSecondWithDash(String time) throws ParseException {
        return convert(time, SECOND_WITH_DASH);
    }

    /**
     * 从yyyyMMddHHmmss的String类型转化为Date
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date convertFromSecondWithoutDash(String time) throws ParseException {
        return convert(time, SECOND_WITHOUT_DASH);
    }

    /**
     * 获取1970-01-01 00:00:00
     * 
     * @return
     */
    public static Date defaultDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 将Date转化为yyyy-MM-dd HH:mm:ss的String类型,与convertFromSecond相反
     * 
     * @param date
     * @return
     */
    public static String convertToSecondString(Date date) {
        return convert(date, SECOND_WITH_DASH);
    }

    /**
     * 将Date转化为yyyy-MM-dd的String类型,与convertFromDayWithDash相反
     * 
     * @param date
     * @return
     */
    public static String convertToDayWithDashString(Date date) {
        return convert(date, DAY_WITH_DASH_FORMAT);
    }

    /**
     * 根据yyyy-MM-dd的String类型 获取今天的起始Date
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date getDayBeginFromDayWithDash(String time) throws ParseException {
        return convertFromDayWithDash(StringUtils.trim(time));
    }

    /**
     * 根据yyyy-MM-dd的String类型 获取今天的起始Date
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date getDayEndFromDayWithDash(String time) throws ParseException {
        return convertFromSecondWithDash(StringUtils.trim(time) + " 23:59:59");
    }
}
