package log;

import java.io.File;


/**
 * @author Archmage
 */
public class Constants {
	public static final String ACCESS_ROOT = System.getProperty("user.dir")+ File.separator + "access";
	public static final String LOG_ROOT = System.getProperty("user.dir")+ File.separator + "log";
	public static final String DB_ROOT = System.getProperty("user.dir") + File.separator + "db";
	public static final String BCP_ROOT = System.getProperty("user.dir") + File.separator + "bcp";
	
	
	public static final String DB_TABLES = System.getProperty("user.dir") + File.separator + "db"+ File.separator + "create.sql";
	public static final String DB_UPDATES = System.getProperty("user.dir") + File.separator + "db"+ File.separator + "update.sql";
	
	/** */
	public static final String TEST_LOGIN = "http://15.6.42.234:8080/inet/monitor/task_list?pagesize=2&page=1&caseId=-1&status=-10&_=";
	
	
	
	
	
	public static void main(String [] args){
	}
}
