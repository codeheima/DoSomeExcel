package bao_excel.org.bao.excel.memory;

import java.util.List;
import java.util.Map;

import util.ComplexUtil;
import util.EmptyUtil;
import util.IOUtil;


public enum ExConfig {
	INSTANCE;
	
	private static final String  path = "excel_config.txt";
	
	public Map<String,String> configMap = ComplexUtil.map();
	
	private ExConfig(){
		
		List<String> list =IOUtil.getListString(path,"utf-8");
		List<String> targets = ComplexUtil.list();
		for(String s : list){
			if(EmptyUtil.isEmptyStr(s)){
				continue;
			}
			if(s.startsWith("#")){
				continue;
			}
			if(s.contains("=")){
				targets.add(s);
			}
		}
		
		for(String s : targets){
			int eqIndex = s.indexOf("=");
			
			String key = s.substring(0, eqIndex);
			String val = s.substring(eqIndex+1);
			if(EmptyUtil.isEmptyStr(val)){
				val = "";
			}
			configMap.put(key, val);
		}
		
	}
	
	public String getVal(String code){
		if(configMap.containsKey(code)){
			return configMap.get(code);
		}
		return "";
	}
	
	public static String val(String code){
		
		return ExConfig.INSTANCE.getVal(code);
	}
	
	
	public static void main(String [] args){
		System.out.println(ExConfig.val("user.detail.reason"));
	}
}
