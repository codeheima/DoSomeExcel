package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Types;

import util.DateGet;

import net.sf.json.JSONObject;



/**
 * @author Archmage
 */
public class MyLog {
	public static final String fileName = "log.txt";
	
	public static String filePath = Constants.LOG_ROOT + File.separator + fileName;
	
	private static File logFile = null;
	static{
		File f = new File(Constants.LOG_ROOT);
		if(!f.exists()){
			f.mkdirs();
		}
		f = new File(filePath);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logFile = f;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
	public static void init() {
		
	}
	
	public static void debug(String content){
		print(content,"DEBUG");
	}
	
	public static void error(String content){
		print(content,"ERROR");
	}
	public static void error(String msg,Throwable err){
		error(msg + err.getMessage());
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] eles = err.getStackTrace();
		for(StackTraceElement e : eles){
			sb.append(String.valueOf(e)).append("\r\n");
		}
		error(sb.toString());
	}
	
	private static void print(String content, String mark) {
		BufferedWriter bw =  null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile,true)));
			bw.write(mark + ":[");
			
			bw.write(DateGet.nowDateStr());
			bw.write(']');
			bw.write(content);
			bw.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
