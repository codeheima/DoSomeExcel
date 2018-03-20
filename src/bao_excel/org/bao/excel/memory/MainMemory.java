package bao_excel.org.bao.excel.memory;



/**
 * @author Archmage
 */
public class MainMemory {
	public static final int HTTP_TYPE_COMMON = 1;
	public static final int HTTP_TYPE_RESEARCH = 2;
	
	public static final String userDir = System.getProperty("user.dir");
	

	
	public static String getPath(String path){
		return userDir +"/"+ path;
	}
	
	public static void init() {
	
	}
	
	
	

	
	public static void main(String [] args){
		System.out.println(System.getProperty("user.dir"));
	}
	
}
