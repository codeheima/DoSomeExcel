package util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 鏃ユ湡澶勭悊宸ュ叿
 * @author ryan
 *
 */
public class DateUtils {
	
	//浼犺緭鏍煎紡
	public final static String TRANS_DATE_FORMAT = "yyyyMMddHHmmss";
	
	//闀挎棩鏈熸牸锟�
	public final static String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	//鐭棩鏈熸牸锟�
	public final static String SHORT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	//鏃ユ湡鏍煎紡
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	
	//鏃堕棿鏍煎紡
	public final static String TIME_FORMAT_HHmm = "HH:mm";
	
	//锟�锟斤拷鐨勬渶鍒濇椂锟�
	public final static String FIRST_TIME_FORMAT = "yyyy-MM-dd 00:00:00.000";
	
	//锟�锟斤拷鐨勬渶鍚庢椂锟�
	public final static String LAST_TIME_FORMAT = "yyyy-MM-dd 23:59:59.999";
	
	//鏃ユ湡鏍煎紡
	public final static String[] DATE_PATTERNS = 
					new String[]{"yyyy-MM-dd HH:mm:ss.SSS","yyyy/MM/dd HH:mm:ss.SSS","MM/dd/yyyy HH:mm:ss.SSS",
								"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss","MM/dd/yyyy HH:mm:ss",
								"yyyy-MM-dd HH:mm","yyyy/MM/dd HH:mm","MM/dd/yyyy HH:mm",
								"yyyy-MM-dd HH","yyyy/MM/dd HH","MM/dd/yyyy HH",
								"yyyy-MM-dd","yyyy/MM/dd","MM/dd/yyyy"};

	
	
	/**
	 * 灏嗕竴涓瓧绗︿覆鎸夐粯璁ゆ牸寮忚浆鎹负鏃ユ湡瀵硅薄
	 * @param str		鏃ユ湡瀛楃锟�
	 * @return
	 */
	public static Date parseDate(long d){
		return new Date(d);
	}	
	/**
	 * 灏嗕竴涓瓧绗︿覆鎸夐粯璁ゆ牸寮忚浆鎹负鏃ユ湡瀵硅薄
	 * @param str		鏃ユ湡瀛楃锟�
	 * @return
	 */ 
	public static Date parseDate(String str){
		return parseDate(str,DATE_PATTERNS);
	}	
	/**
	 * 灏嗕竴涓瓧绗︿覆鎸夋寚瀹氭牸寮忚浆鎹负鏃ユ湡瀵硅薄
	 * @param src		瑕佽浆鎹㈢殑瀛楃锟�
	 * @param format	鏃ユ湡鏍煎紡
	 * @return			
	 */
	public static Date parseDate(String src,String format){
		Date date = null;
		if(src==null || src.trim().equals("") || src.trim().equals("null") 
				|| src.trim().equals("NULL")){
			return null;
		}
		try{
			date=new SimpleDateFormat(format).parse(src.trim());
		}catch(Exception e){}
		return date;
	}
	/**
	 * 灏嗕竴涓瓧绗︿覆鎸夋寚瀹氭牸寮忚浆鎹负鏃ユ湡瀵硅薄
	 * @param str			瑕佽浆鎹㈢殑瀛楃锟�
	 * @param parsePatterns	鍙兘鍑虹幇鐨勬墍鏈夋棩鏈熸牸锟�
	 * @return
	 * @throws ParseException
	 */
    public static Date parseDate(String str, String[] parsePatterns){
        if (str == null  || str.trim().equals("") || parsePatterns == null) {
            return null;
        }
        
        SimpleDateFormat parser = null;
        ParsePosition pos = new ParsePosition(0);
        for (int i = 0; i < parsePatterns.length; i++) {
            if (i == 0) {
                parser = new SimpleDateFormat(parsePatterns[0]);
            } else {
                parser.applyPattern(parsePatterns[i]);
            }
            pos.setIndex(0);
            Date date = parser.parse(str, pos);
            if (date != null && pos.getIndex() == str.length()) {
                return date;
            }
        }
        return null;
    }	
    /**
     * 灏嗕竴涓牸寮忕殑鏃ユ湡瀛楃涓茶浆鎹负鍙︿竴绉嶆牸寮忕殑鏃ユ湡瀛楃锟�
     * @param str			鏃ユ湡瀛楃锟�
     * @param srcFormat		婧愬瓧绗︿覆鏍煎紡
     * @return
     */
    public static String convertDateFormat(String str,String dstFormat){
    	return convertDateFormat(str,DATE_PATTERNS,dstFormat);
    }
    /**
     * 灏嗕竴涓牸寮忕殑鏃ユ湡瀛楃涓茶浆鎹负鍙︿竴绉嶆牸寮忕殑鏃ユ湡瀛楃锟�
     * @param str			鏃ユ湡瀛楃锟�
     * @param srcFormat		婧愬瓧绗︿覆鏍煎紡
     * @param dstFormat		鐩爣瀛楃涓叉牸锟�
     * @return
     */
    public static String convertDateFormat(String str,String srcFormat, String dstFormat){
    	return convertDateFormat(str,new String[]{srcFormat},dstFormat);
    }  
    /**
     * 灏嗕竴涓牸寮忕殑鏃ユ湡瀛楃涓茶浆鎹负鍙︿竴绉嶆牸寮忕殑鏃ユ湡瀛楃锟�
     * @param str			鏃ユ湡瀛楃锟�
     * @param srcFormats	婧愬瓧绗︿覆鏍煎紡
     * @param dstFormat		鐩爣瀛楃涓叉牸锟�
     * @return
     */
    public static String convertDateFormat(String str,String[] srcFormats, String dstFormat){
    	if(str==null || str.trim().equals(""))
    		return str;
    	if(srcFormats==null || srcFormats.length==0 || dstFormat==null)
    		throw new IllegalArgumentException("鏃ユ湡鏍煎紡涓嶈兘涓虹┖");

    	Date date = parseDate(str,srcFormats);
    	if(date==null)
    		return null;
    	else
    		return formatDate(date,dstFormat);
    } 
	/**
	 * 灏嗕竴涓棩鏈熷璞℃寜鎸囧畾鏍煎紡杞崲涓哄瓧绗︿覆
	 * @param date			鏃ユ湡姣锟�
	 * @param format		鏃ユ湡鏍煎紡
	 * @return
	 */
	public static String formatDate(long d,String format){
		return new SimpleDateFormat(format).format(new Date(d));
	}    
	/**
	 * 灏嗕竴涓棩鏈熷璞℃寜鎸囧畾鏍煎紡杞崲涓哄瓧绗︿覆
	 * @param date			鏃ユ湡瀵硅薄
	 * @param format		鏃ユ湡鏍煎紡
	 * @return
	 */
	public static String formatDate(Date d,String format){
		if(d==null)
			return null;
		else
			return new SimpleDateFormat(format).format(d);
	}
	
	/**
	 * 鍦ㄦ棩鏈熸寚瀹氫綅缃笂鍔犱笂鏌愶拷?鍚庤幏寰椾竴涓柊鐨勬椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param field		浣嶇疆
	 * @param time		鏃堕棿
	 * @return
	 */
	public static Date add(Date date, int field, int time){
		if(date==null || time==0)
			return date;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, time);
		return calendar.getTime();
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婂姞涓婃寚瀹氱殑骞存暟鍚庤繑鍥炰竴涓柊鐨勬棩鏈熷锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param year		骞存暟
	 * @return
	 */
	public static Date addYear(Date date, int year){
		return add(date,Calendar.YEAR,year);
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婂姞涓婃寚瀹氱殑鏈堟暟鍚庤繑鍥炰竴涓柊鐨勬棩鏈熷锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param month		鏈堟暟
	 * @return
	 */
	public static Date addMonth(Date date, int month){
		return add(date,Calendar.MONTH,month);
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婂姞涓婃寚瀹氱殑澶╂暟鍚庤繑鍥炰竴涓柊鐨勬棩鏈熷锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param day		澶╂暟
	 * @return
	 */
	public static Date addDay(Date date, int day){
		return add(date,Calendar.DATE,day);
	}		
	/**
	 * 鍦ㄥ師鏃ユ湡涓婂姞涓婃寚瀹氱殑灏忔椂鏁板悗杩斿洖锟�锟斤拷鏂扮殑鏃ユ湡瀵硅薄
	 * @param date		鏃ユ湡瀵硅薄
	 * @param hour		灏忔椂锟�
	 * @return
	 */
	public static Date addHour(Date date, int hour){
		return add(date,Calendar.HOUR,hour);
	}
	/**
	 * 鍦ㄥ師鏃ユ湡涓婂姞涓婃寚瀹氱殑鍒嗛挓鏁板悗杩斿洖锟�锟斤拷鏂扮殑鏃ユ湡瀵硅薄
	 * @param date		鏃ユ湡瀵硅薄
	 * @param minute	鍒嗛挓锟�
	 * @return
	 */
	public static Date addMinute(Date date, int minute){
		return add(date,Calendar.MINUTE,minute);
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婂姞涓婃寚瀹氱殑绉掓暟鍚庤繑鍥炰竴涓柊鐨勬棩鏈熷锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param second	绉掓暟
	 * @return
	 */
	public static Date addSecond(Date date, int second){
		return add(date,Calendar.SECOND,second);
	}
	/**
	 * 鍦ㄦ棩鏈熸寚瀹氫綅缃笂璧嬫煇璁剧疆锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param field		浣嶇疆
	 * @param time		鏃堕棿
	 * @return
	 */
	public static Date set(Date date, int field, int time){
		if(date==null || time==0)
			return date;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(field, time);
		return calendar.getTime();
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婅缃勾鏁颁负锟�锟斤拷瀹氾拷?
	 * @param date		鏃ユ湡瀵硅薄
	 * @param year		骞存暟
	 * @return
	 */
	public static Date setYear(Date date, int year){
		return set(date,Calendar.YEAR,year);
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婅缃湀鏁颁负锟�锟斤拷瀹氾拷?
	 * @param date		鏃ユ湡瀵硅薄
	 * @param month		鏈堟暟
	 * @return
	 */
	public static Date setMonth(Date date, int month){
		if(month==1)
			month = 12;
		else
			month = month - 1;
		return set(date,Calendar.MONTH,month);
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婅缃ぉ鏁颁负锟�锟斤拷瀹氾拷?
	 * @param date		鏃ユ湡瀵硅薄
	 * @param day		澶╂暟
	 * @return
	 */
	public static Date setDay(Date date, int day){
		return set(date,Calendar.DATE,day);
	}		
	/**
	 * 鍦ㄥ師鏃ユ湡涓婅缃皬鏃舵暟涓轰竴鎸囧畾锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param hour		灏忔椂锟�
	 * @return
	 */
	public static Date setHour(Date date, int hour){
		return set(date,Calendar.HOUR,hour);
	}
	/**
	 * 鍦ㄥ師鏃ユ湡涓婅缃垎閽熸暟涓轰竴鎸囧畾锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @param minute	鍒嗛挓锟�
	 * @return
	 */
	public static Date setMinute(Date date, int minute){
		return set(date,Calendar.MINUTE,minute);
	}	
	/**
	 * 鍦ㄥ師鏃ユ湡涓婅缃鏁颁负锟�锟斤拷瀹氾拷?
	 * @param date		鏃ユ湡瀵硅薄
	 * @param second	绉掓暟
	 * @return
	 */
	public static Date setSecond(Date date, int second){
		return set(date,Calendar.SECOND,second);
	}	
	/**
	 * 寰楀埌褰撳墠鏃ユ湡褰撳ぉ鐨勬渶鏃╂椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getFirstTime(Date date){
		if(date==null)
			return null;
		return parseDate(formatDate(date,FIRST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 寰楀埌褰撳墠鏃ユ湡鏈懆鐨勬渶鏃╂椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getFirstTimeOfWeek(Date date){
		if(date==null)
			return null;
		int day = getDayAtWeek(date);
		return getFirstTime(addDay(date,-day+1));
	}		
	/**
	 * 寰楀埌褰撳墠鏃ユ湡鏈湀鐨勬渶鏃╂椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getFirstTimeOfMonth(Date date){
		if(date==null)
			return null;
		date = setDay(date,1);
		return parseDate(formatDate(date,FIRST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 寰楀埌褰撳墠鏃ユ湡鏈勾鐨勬渶鏃╂椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getFirstTimeOfYear(Date date){
		if(date==null)
			return null;
		date = setDay(date,1);
		date = setMonth(date,1);
		return parseDate(formatDate(date,FIRST_TIME_FORMAT),LONG_DATE_FORMAT);
	}	
	/**
	 * 寰楀埌褰撳墠鏃ユ湡褰撳ぉ鐨勬渶鏅氭椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getLastTime(Date date){
		if(date==null)
			return null;
		return parseDate(formatDate(date,LAST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 寰楀埌褰撳墠鏃ユ湡鏈懆鐨勬渶鏅氭椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getLastTimeOfWeek(Date date){
		if(date==null)
			return null;
		int day = getDayAtWeek(date);
		return getLastTime(addDay(date,7-day));
	}	
	/**
	 * 寰楀埌褰撳墠鏃ユ湡鏈湀鐨勬渶鏃╂椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getLastTimeOfMonth(Date date){
		if(date==null)
			return null;
		date = addMonth(date,1);
		date = setDay(date,1);
		date = addDay(date,-1);
		return parseDate(formatDate(date,LAST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 寰楀埌褰撳墠鏃ユ湡鏈勾鐨勬渶鏅氭椂锟�
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static Date getLastTimeOfYear(Date date){
		if(date==null)
			return null;
		date = setDay(date,1);
		date = setMonth(date,1);
		date = addYear(date,1);
		date = addDay(date,-1);
		return parseDate(formatDate(date,LAST_TIME_FORMAT),LONG_DATE_FORMAT);
	}	
	/**
	 * 寰楀埌鏃ユ湡鍦ㄨ繖锟�锟斤拷鐨勭鍑犲ぉ
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getDayAtYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}	
	/**
	 * 寰楀埌鏃ユ湡鍦ㄨ繖锟�锟斤拷鐨勭鍑犲ぉ
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getDayAtMonth(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 寰楀埌鏃ユ湡鍦ㄨ繖锟�锟斤拷鐨勭鍑犲ぉ
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getDayAtWeek(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}	
	/**
	 * 寰楀埌鏃ユ湡鍦ㄨ繖锟�锟斤拷鐨勭鍑犲懆
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getWeekAtMonth(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	/**
	 * 寰楀埌鏃ユ湡鍦ㄨ繖锟�锟斤拷鐨勭鍑犲懆
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getWeekAtYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 寰楀埌鏃ユ湡鍦ㄨ繖锟�锟斤拷鐨勭鍑犳湀
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getMonthAtYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		if(month==12)
			return 1;
		else
			return month+1;
	}	
	/**
	 * 寰楀埌鏃ユ湡锟�锟斤拷鐨勮繖锟�锟斤拷鏈夊灏戝ぉ
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getDayOfYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}	
	/**
	 * 寰楀埌鏃ユ湡锟�锟斤拷鐨勮繖锟�锟斤拷鏈夊灏戝ぉ
	 * @param date		鏃ユ湡瀵硅薄
	 * @return
	 */
	public static int getDayOfMonth(Date date){
		Date d1 = setDay(date,1);
		Date d2 = addMonth(d1,1);
		return getDayOfBetweenDate(d2,d1);
	}
	/**
	 * 寰楀埌涓ゆ棩鏈熼棿闂寸浉宸殑澶╂暟
	 * @param date1		锟�锟斤拷鏃ユ湡
	 * @param date2		缁撴潫鏃ユ湡
	 * @return
	 */
	public static int getDayOfBetweenDate(Date date1,Date date2){
		if(date1==null || date2==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		long l = date1.getTime() - date2.getTime();
		long days = l/1000/60/60/24;
		
		return new Long(days).intValue();
	}
	/**
	 * 寰楀埌涓ゆ棩鏈熼棿闂寸浉宸殑灏忔椂锟�
	 * @param date1		锟�锟斤拷鏃ユ湡
	 * @param date2		缁撴潫鏃ユ湡
	 * @return
	 */
	public static int getHourOfBetweenDate(Date date1,Date date2){
		if(date1==null || date2==null)
			throw new IllegalArgumentException("鏃ユ湡涓嶈兘涓虹┖");
		long l = date1.getTime() - date2.getTime();
		long days = l/1000/60/60;
		
		return new Long(days).intValue();
	}
	
	/**
	 * 寰楀埌褰撳墠鏃堕棿鎸囧畾澶╂暟鍓嶅悗鐨勫紑濮嬫椂锟�
	 * @param day	澶╂暟锛屾鏁板垯涓轰箣鍚庯紝璐熸暟涓轰箣锟�
	 * @return
	 */
	public static Date getBeginTime(int day){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, day);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		return time.getTime();
	}
	
	/**
	 * 寰楀埌褰撳墠鏃堕棿鎸囧畾澶╂暟鍓嶅悗鐨勭粨鏉熸椂锟�
	 * @param day	澶╂暟锛屾鏁板垯涓轰箣鍚庯紝璐熸暟涓轰箣锟�
	 * @return
	 */
	public static Date getEndTime(int day){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, day);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		time.set(Calendar.MILLISECOND, 999);
		
		return time.getTime();
	}
	
	/**
	 * 寰楀埌褰撳墠鏃堕棿鎸囧畾鍛ㄦ暟鍓嶅悗鐨勫紑濮嬫椂锟�
	 * @param week	鍛ㄦ暟锛屾鏁板垯涓轰箣鍚庯紝璐熸暟涓轰箣锟�
	 * @return
	 */
	public static Date getBeginTimeOfWeek(int week){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.WEEK_OF_YEAR, week);
		time.set(Calendar.DAY_OF_WEEK, 2);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		return time.getTime();
	}
	
	public static Date getBeginTimeOfYear(){
		Calendar c = Calendar.getInstance();
	//	c.set(Calendar.YEAR, );
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		
		return c.getTime();
	}
	
	
	/**
	 * 寰楀埌褰撳墠鏃堕棿鎸囧畾鍛ㄦ暟鍓嶅悗鐨勫紑濮嬫椂锟�
	 * @param week	鍛ㄦ暟锛屾鏁板垯涓轰箣鍚庯紝璐熸暟涓轰箣锟�
	 * @return
	 */
	public static Date getEndTimeOfWeek(int week){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.WEEK_OF_YEAR, week+1);
		time.set(Calendar.DAY_OF_WEEK, 2);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		time.set(Calendar.MILLISECOND, 999);
		time.add(Calendar.DATE, -1);
		return time.getTime();
	}
	
	/**
	 * 寰楀埌褰撳墠鏃堕棿鎸囧畾鏈堟暟鍓嶅悗鐨勫紑濮嬫椂锟�
	 * @param month	鏈堟暟锛屾鏁板垯涓轰箣鍚庯紝璐熸暟涓轰箣锟�
	 * @return
	 */
	public static Date getBeginTimeOfMonth(int month){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, month);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		return time.getTime();
	}
	
	/**
	 * 寰楀埌褰撳墠鏃堕棿鎸囧畾鏈堟暟鍓嶅悗鐨勭粨鏉熸椂锟�
	 * @param month	鏈堟暟锛屾鏁板垯涓轰箣鍚庯紝璐熸暟涓轰箣锟�
	 * @return
	 */
	public static Date getEndTimeOfMonth(int month){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, month+1);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		time.set(Calendar.MILLISECOND, 999);
		time.add(Calendar.DATE, -1);
		return time.getTime();
	}
	
	public static void main(String args[]){
		Date date = new Date();
		System.out.println("getFirstTime="+formatDate(getFirstTime(date),LONG_DATE_FORMAT));
		System.out.println("getLastTime="+formatDate(getLastTime(date),LONG_DATE_FORMAT));
		
		System.out.println("getFirstTimeOfWeek="+formatDate(getFirstTimeOfWeek(date),LONG_DATE_FORMAT));
		System.out.println("getLastTimeOfWeek="+formatDate(getLastTimeOfWeek(date),LONG_DATE_FORMAT));
		
		System.out.println("getFirstTimeOfMonth="+formatDate(getFirstTimeOfMonth(date),LONG_DATE_FORMAT));
		System.out.println("getLastTimeOfMonth="+formatDate(getLastTimeOfMonth(date),LONG_DATE_FORMAT));	
		
		System.out.println("getFirstTimeOfYear="+formatDate(getFirstTimeOfYear(date),LONG_DATE_FORMAT));
		System.out.println("getLastTimeOfYear="+formatDate(getLastTimeOfYear(date),LONG_DATE_FORMAT));		
		
		System.out.println("getDayAtYear="+getDayAtYear(date));
		System.out.println("getDayAtMonth="+getDayAtMonth(date));		
		System.out.println("getDayAtWeek="+getDayAtWeek(date));		
		System.out.println("getWeekAtMonth="+getWeekAtMonth(date));
		System.out.println("getWeekAtYear="+getWeekAtYear(date));
		System.out.println("getMonthAtYear="+getMonthAtYear(date));
		
		System.out.println("getDayOfYear="+getDayOfYear(date));
		System.out.println("getDayOfMonth="+getDayOfMonth(date));
		
		System.out.println("getDayOfBetweenDate="+getDayOfBetweenDate(getLastTimeOfMonth(date),date));
		System.out.println("getHourOfBetweenDate="+getHourOfBetweenDate(getLastTimeOfMonth(date),date));
		
		System.out.println("--------------------------");
		
		date = new Date();
		
	
	}
}
