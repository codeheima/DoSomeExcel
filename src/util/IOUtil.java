package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class IOUtil {
	
	public static String getStreamData(InputStream is) {
		return getStreamData(is,null);
	}
	
	public static String getStreamData(InputStream is,String charSet) {
		if(null == charSet)
			charSet = "utf-8";
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int index = 0;
			while ((index = is.read(data)) != -1) {
				bos.write(data, 0, index);
			}
			return new String(bos.toByteArray(), charSet);
		} catch (Exception e) {
			return null;
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
				}
		}
		
		
	}

	public static List<String> getListString(String path) {
		InputStream is = ReflectUtil.getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		List<String> list = ComplexUtil.list();
		try {
			
			String line = br.readLine();
			
			while(line != null){
				list.add(line.trim());
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return list;
	}
	
	public static List<String> getListString(String path,String charSet) {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream(new File(path));
			br = new BufferedReader(new InputStreamReader(is,charSet));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> list = ComplexUtil.list();
		try {
			
			String line = br.readLine();
			
			while(line != null){
				list.add(line.trim());
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return list;
	}
}
