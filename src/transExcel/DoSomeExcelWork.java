package transExcel;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import log.MyLog;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.ComplexUtil;
import bao_excel.org.bao.excel.memory.MainMemory;
import bao_excel.org.bao.excel.poi.PoiUtil;
import bao_excel.org.bao.excel.poi.bean.BeanSheet2;

public class DoSomeExcelWork {
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					TransBuExcel excel = new TransBuExcel();
					excel.initbu(MainMemory.getPath("bu/src"));
					Map<String, List<BeanSheet2>> mapc = excel.bu.caseMap;
					Map<String, Map<String, List<BeanSheet2>>> map = excel.dealMap(mapc);
					Set<String> set = map.keySet();
					for(String key:set){
						Map<String, List<BeanSheet2>> cMap = map.get(key);
						for(Entry<String, List<BeanSheet2>> entry :cMap.entrySet()){
							File tempfile = new File(MainMemory.getPath("bu_template.xlsx"));
							FileInputStream is = new FileInputStream(tempfile);
							XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
							String timeZone = entry.getKey();
							List<BeanSheet2> ccList = entry.getValue();
							List<BeanSheet2> tempList = ComplexUtil.list();
							Set<String> set2 = ComplexUtil.set();
							for(BeanSheet2 sheet2 :ccList){
								if(set2.contains(sheet2.getAccount())){
									continue;
								}else {
									set2.add(sheet2.getAccount());
									tempList.add(sheet2);
								}
							}
							excel.writeToDestExcel(tempList,xssfWorkbook,timeZone);
							
							SXSSFWorkbook sWorkBook = new SXSSFWorkbook(xssfWorkbook,100);
							
							String dirPath = MainMemory.getPath("bu")+"/dest/"+key;
							
							File file = new File(dirPath);
							if(!file.exists()){
								file.mkdirs();
							}
							PoiUtil.doOutFile(sWorkBook,MainMemory.getPath("bu")+"/dest/"+key+"/"+timeZone+".xlsx");
							MyLog.debug("已处理一个文件");
							is.close();
							
						}
						
					}
				} catch (Exception e) {
					MyLog.error(e.getMessage(),e);
				}
				
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					TransStExcel excel = new TransStExcel();
					excel.initMap();
					excel.initst(MainMemory.getPath("st/src"));
					Map<String, List<BeanSheet2>> mapc = excel.st.caseMap;
					Map<String, Map<String, List<BeanSheet2>>> map = excel.dealMap(mapc);
					Set<String> set = map.keySet();
					for(String key:set){
						Map<String, List<BeanSheet2>> cMap = map.get(key);
						for(Entry<String, List<BeanSheet2>> entry :cMap.entrySet()){
							File tempfile = new File(MainMemory.getPath("st_template.xlsx"));
							FileInputStream is = new FileInputStream(tempfile);
							XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
							String timeZone = entry.getKey();
							List<BeanSheet2> ccList = entry.getValue();
							List<BeanSheet2> tempList = ComplexUtil.list();
							Set<String> set2 = ComplexUtil.set();
							for(BeanSheet2 sheet2 :ccList){
								if(set2.contains(sheet2.getAccount())){
									continue;
								}else {
									set2.add(sheet2.getAccount());
									tempList.add(sheet2);
								}
							}
							excel.writeToDestExcel(tempList,xssfWorkbook,timeZone);
							
							SXSSFWorkbook sWorkBook = new SXSSFWorkbook(xssfWorkbook,100);
							
							String dirPath = MainMemory.getPath("st")+"/dest/"+key;
							
							File file = new File(dirPath);
							if(!file.exists()){
								file.mkdirs();
							}
							PoiUtil.doOutFile(sWorkBook,MainMemory.getPath("st")+"/dest/"+key+"/"+timeZone+".xlsx");
							MyLog.debug("已处理一个文件");
							is.close();
							
						}
						
					}
				} catch (Exception e) {
					MyLog.error(e.getMessage(),e);
				}
				
			}
		}).start();
	}

}
