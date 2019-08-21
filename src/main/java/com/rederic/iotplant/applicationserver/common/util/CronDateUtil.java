package com.rederic.iotplant.applicationserver.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CronDateUtil {
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";
    private static final String TASK_DATE = " dd MM ? yyyy";
    private static final String TASK_TIME = "ss mm HH";
    
    /**
    * @Title: getCron
    * @Description: TODO(输入Date类型的时间日期转换为cron，用于做指定时间的定时任务)
    * @param @param date
    * @param @param time
    * @param @return    参数
    * @return String    返回类型
    * @throws
    */
    public static String getTaskCron(Date date,Date time) {
    	SimpleDateFormat sdftime = new SimpleDateFormat(TASK_TIME);
    	SimpleDateFormat sdfdate = new SimpleDateFormat(TASK_DATE);
    	String result = "";
    	if(time!=null) {
    		result += sdftime.format(time);
    	}
    	if(date != null) {
    		result += sdfdate.format(date);
    	}
    	return result;
    	
    }
    
    public static String getTaskCron(Date time) {
    	SimpleDateFormat sdftime = new SimpleDateFormat(TASK_TIME);
    	String result = "";
    	if(time!=null) {
    		result += sdftime.format(time);
    	}
    	result += " * * ?";
    	return result;
    	
    }
    
    /***
     *
     * @param date 时间
     * @return  cron类型的日期
     */
    public static String getCron(final Date  date){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
 
    /***
     *
     * @param cron Quartz cron的类型的日期
     * @return  Date日期
     */
 
    public static Date getDate(final String cron) {
 
 
        if(cron == null) {
            return null;
        }
 
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;// 此处缺少异常处理,自己根据需要添加
        }
        return date;
    }
    
    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(now);
        System.out.println(CronDateUtil.getCron(now));
 
        String cron = "20 28 17 02 08 ? 2016";
 
        Date cronDate = CronDateUtil.getDate(cron);
        System.out.println("===================");
        System.out.println(cronDate.toString());
 
 
    }

}
