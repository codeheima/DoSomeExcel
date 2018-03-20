package util;

import java.util.Calendar;
import java.util.Date;


/**
 * @author Archmage
 */
public class DateGet {
	
	public static long now(){
		return System.currentTimeMillis();
	}
	
	
	public static String nowDateStr(){
		return DateUtils.formatDate(new Date(),DateUtils.SHORT_DATE_FORMAT);
	}
	
	public static String timeDateStrHHmm(){
		return DateUtils.formatDate(new Date(),DateUtils.TIME_FORMAT_HHmm);
	}
	
	
	private static long declong = Long.MIN_VALUE;
	
	/** 将转成的long类型时间 转成 阵地系统中的long时间	 */
	public static long timeDateStrToLong(String timeString){
		if(declong == Long.MIN_VALUE){
			//startTime:1467516074
			//startTimeStr:2016-07-03 11:21:14
			String timeStr = "2016-07-03 11:21:14";
			Date d = DateUtils.parseDate(timeStr, DateUtils.SHORT_DATE_FORMAT);
			long dNow = d.getTime()/1000;
			long dZD = 1467516074;
	//		System.out.println("dNow:"+dNow);
	
	//		System.out.println("时间�?本机比阵地多)�?+(dNow - dZD));
			declong = dNow - dZD;
		}
		Date dd = DateUtils.parseDate(timeString, DateUtils.SHORT_DATE_FORMAT);
		long dTime = dd.getTime()/1000;

	//	System.out.println("输出转换后阵地long时间�?"+ (dTime-(dNow - dZD)) );
		return dTime-(declong);
	}
	
	
	public static String addTimeByFiled(String srcTime,String format,int filed ,int add){
		Date date = DateUtils.parseDate(srcTime, format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(filed, add );
		return DateUtils.formatDate(c.getTime(), format);
	}
	
	public static String getLastTen(){
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, -10 );
		String lastTime = DateUtils.formatDate(c.getTime(), DateUtils.SHORT_DATE_FORMAT);
		return lastTime;
	}
	
	public static Date getLastDate(Date d,int day){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, -day );
		return c.getTime();
	}
	
	public static void main(String[] args){
	//	timeDateStrToLong("2016-07-06 11:21:13");
		Date d= new Date();
		Date d1 = getLastDate(d,3);
		System.out.println(d.getTime());
		System.out.println(d1.getTime());
		
		System.out.println(timeDateStrToLong("2016-07-03 12:21:14"));
		
		System.out.println(addTimeByFiled("2016-07-03 12:21:14",DateUtils.SHORT_DATE_FORMAT,Calendar.HOUR_OF_DAY,-10));
	}
}
