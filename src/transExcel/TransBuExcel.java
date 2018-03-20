package transExcel;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import log.MyLog;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.ComplexUtil;
import util.DateUtils;
import util.EmptyUtil;
import bao_excel.org.bao.excel.memory.ExConfig;
import bao_excel.org.bao.excel.memory.MainMemory;
import bao_excel.org.bao.excel.poi.PoiUtil;
import bao_excel.org.bao.excel.poi.bean.BeanSheet2;
import bao_excel.org.bao.excel.poi.bean.BuBean;

public class TransBuExcel {
	
	BuBean bu = null;
	
	public void initbu(String path) throws Exception{
		bu = new BuBean();
		File file = new File(path);
		File[] files = null;
		if(file.isDirectory()){
			files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					String fileName = pathname.getName();
					return fileName.endsWith(".xls")||fileName.endsWith(".xlsx");
				}
			});
		}
		for(File cfile :files){
			String fileName = cfile.getName();
			if(fileName.endsWith(".xlsx")){
				FileInputStream is = new FileInputStream(cfile);
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
				XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
				for(int rowNum = 0;rowNum<=sheet.getLastRowNum();rowNum++){
					XSSFRow row = sheet.getRow(rowNum);
					String action = row.getCell(4).getStringCellValue();
					String searchContent = row.getCell(5).getStringCellValue();
					if("查询".equals(action)&&searchContent.contains("综合查询")){
						String recordTime = PoiUtil.getValue(row.getCell(7));
						searchContent = searchContent.substring
								(searchContent.indexOf("【"),searchContent.lastIndexOf("】")+1);
						String appType = "";
						String account = "";
						String userName = PoiUtil.getValue(row.getCell(0));
						String caseName = "";
						String[] contextArr = searchContent.split("；");
						for(String arrStr :contextArr){
							if(arrStr.contains("应用类型")){
								appType = arrStr.substring(arrStr.indexOf("：")+1);
								if(!EmptyUtil.isEmptyStr(account)&&!EmptyUtil.isEmptyStr(caseName)){
									break;
								}
							}
							if(arrStr.contains("查询值")){
								account = arrStr.substring(arrStr.indexOf("：")+1);
								if(!EmptyUtil.isEmptyStr(appType)&&!EmptyUtil.isEmptyStr(caseName)){
									break;
								}
							}
							if(arrStr.contains("案件")){
								caseName = arrStr.substring(arrStr.indexOf("：")+1);
								caseName = caseName.replace("】", "");
								if(!EmptyUtil.isEmptyStr(appType)&&!EmptyUtil.isEmptyStr(account)){
									break;
								}
							}
							
						}
						if("null".equals(appType)){
							appType = "";
						}
						BeanSheet2 bean = new BeanSheet2(appType, account, recordTime, caseName, userName);
						if(!EmptyUtil.isEmptyStr(account)){
							if (bu.caseMap.containsKey(caseName)) {
								bu.caseMap.get(caseName).add(bean);
							}else {
								List<BeanSheet2> tempList = ComplexUtil.list();
								tempList.add(bean);
								bu.caseMap.put(caseName,tempList);
							}
						}
						//bu.secondlist.add(new BeanSheet2(appType, account, recordTime,caseName,userName));
						
						
					}
				}
			}else {
				FileInputStream is = new FileInputStream(cfile);
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
				HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
				for(int rowNum = 0;rowNum<=sheet.getLastRowNum();rowNum++){
					HSSFRow row = sheet.getRow(rowNum);
					String action = row.getCell(4).getStringCellValue();
					String searchContent = row.getCell(5).getStringCellValue();
					if("查询".equals(action)&&searchContent.contains("综合查询")){
						String recordTime = PoiUtil.getValue(row.getCell(7));
						searchContent = searchContent.substring
								(searchContent.indexOf("【"),searchContent.lastIndexOf("】")+1);
						String appType = "";
						String account = "";
						String userName = PoiUtil.getValue(row.getCell(0));
						String caseName = "";
						String[] contextArr = searchContent.split("；");
						for(String arrStr :contextArr){
							if(arrStr.contains("应用类型")){
								appType = arrStr.substring(arrStr.indexOf("：")+1);
								if(!EmptyUtil.isEmptyStr(account)&&!EmptyUtil.isEmptyStr(caseName)){
									break;
								}
							}
							if(arrStr.contains("查询值")){
								account = arrStr.substring(arrStr.indexOf("：")+1);
								if(!EmptyUtil.isEmptyStr(appType)&&!EmptyUtil.isEmptyStr(caseName)){
									break;
								}
							}
							if(arrStr.contains("案件")){
								caseName = arrStr.substring(arrStr.indexOf("：")+1);
								caseName = caseName.replace("】", "");
								if(!EmptyUtil.isEmptyStr(appType)&&!EmptyUtil.isEmptyStr(account)){
									break;
								}
							}
							
						}
						if("null".equals(appType)){
							appType = "";
						}
						BeanSheet2 bean = new BeanSheet2(appType, account, recordTime, caseName, userName);
						if(!EmptyUtil.isEmptyStr(account)){
							if (bu.caseMap.containsKey(caseName)) {
								bu.caseMap.get(caseName).add(bean);
							}else {
								List<BeanSheet2> tempList = ComplexUtil.list();
								tempList.add(bean);
								bu.caseMap.put(caseName,tempList);
							}
						}
						//bu.secondlist.add(new BeanSheet2(appType, account, recordTime,caseName,userName));
						
						
					}
				}
			}
			cfile.renameTo(new File(MainMemory.getPath("bu")+"/bak/"+fileName));
			if(cfile.exists()){
				cfile.delete();
			}
			MyLog.debug("转移文件成功");
		}
	
	}
	
	public void writeToDestExcel(List<BeanSheet2> list,XSSFWorkbook xssfWorkbook,String timeZone) throws Exception{
		if(EmptyUtil.isEmptyList(list)){
			return;
		}
		Collections.sort(list, new Comparator<BeanSheet2>() {

			@Override
			public int compare(BeanSheet2 o1, BeanSheet2 o2) {
				return o1.getRecordTime().compareTo(o2.getRecordTime());
			}
		});
		ExConfig exConfig = ExConfig.INSTANCE;
		int day = Integer.valueOf(exConfig .getVal("bu.outer.day"));
		//int month = Integer.valueOf(exConfig .getVal("bu.outer.month"));
		BeanSheet2 beanSheet2 = list.get(0);
		
		String recordTime = beanSheet2.getRecordTime();
		
		Date firstDate = DateUtils.parseDate(recordTime, DateUtils.DATE_FORMAT);
		
		recordTime = DateUtils.formatDate(firstDate, DateUtils.DATE_FORMAT);
		
		Date endDate = DateUtils.addDay(firstDate, day);
		
		xssfWorkbook.cloneSheet(xssfWorkbook.getNumberOfSheets()-1);
		
		XSSFSheet sheet = xssfWorkbook.getSheetAt(xssfWorkbook.getNumberOfSheets()-2);
		
		int current = 5;
		
		XSSFCellStyle style = PoiUtil.getStyle(sheet,5,2);
		
		//XSSFCellStyle style2 = PoiUtil.getStyle(sheet,4,3);
		
		List<BeanSheet2> tempList = ComplexUtil.list();
		for(int i = 0; i<list.size(); i++ ){
			BeanSheet2 bean = list.get(i);;
			String record = bean.getRecordTime();
			if(DateUtils.parseDate(record, DateUtils.DATE_FORMAT).after(endDate)){
				tempList.add(list.get(i));
				continue;
			}
			XSSFRow rowNew = PoiUtil.createRow(sheet,current);
			Cell c0 = rowNew.createCell(0);
			c0.setCellStyle(style);
			Cell c = rowNew.createCell(1);
			c.setCellStyle(style);
			c.setCellValue(bean.getAppType());
			Cell c2 = rowNew.createCell(2);
			c2.setCellStyle(style);
			c2.setCellValue(bean.getAccount());
		}
		int n = list.size()-tempList.size();
		sheet.shiftRows(6+n, sheet.getLastRowNum(), -1);
		
		PoiUtil.unionArea(sheet,4,4+n,0,0);
		
		XSSFSheet sheet2 = xssfWorkbook.getSheetAt(0);
		String createUser = ExConfig.INSTANCE.getVal("bu.user.createUser");
		if(EmptyUtil.isEmptyStr(createUser)){
			createUser = list.get(0).getUserName();
		}
		String caseName = ExConfig.INSTANCE.getVal("bu.user.caseName");
		if(EmptyUtil.isEmptyStr(caseName)){
			caseName = list.get(0).getCaseName();
		}
		PoiUtil.changeCell(sheet2, 12, 0,"填报人:"+createUser);
		
		PoiUtil.changeCell(sheet2, 5, 1,list.get(0).getAccount());
			
		PoiUtil.changeCell(sheet2, 6, 1,timeZone);
		
		PoiUtil.changeCell(sheet2, 4, 1,ExConfig.INSTANCE.getVal("bu.user.detail.reason"));
		
		PoiUtil.changeCell(sheet2, 2, 1,caseName);
		
		PoiUtil.changeCell(sheet2, 3, 1,caseName);
		
		PoiUtil.changeCell(sheet2, 12, 2, "填表时间:"+timeZone.substring(0,timeZone.indexOf("至")));
		
		PoiUtil.changeCell(sheet, 1, 1, caseName);
		
		PoiUtil.changeCell(sheet, 2, 1, list.get(0).getUserName());
		
		PoiUtil.changeCell(sheet, 3, 1, recordTime);
		
		writeToDestExcel(tempList,xssfWorkbook,timeZone);
	}
	
	public static void main(String[] args) {
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
	
	public Map<String, Map<String, List<BeanSheet2>>> dealMap(Map<String, List<BeanSheet2>> map){
		Map<String, Map<String, List<BeanSheet2>>> tMap = ComplexUtil.map();
		Set<String> set = map.keySet();
		for(String key:set){
			List<BeanSheet2> tempList = map.get(key);
			Map<String, List<BeanSheet2>> cMap = ComplexUtil.map();
			dealCcList(cMap, tempList);
			tMap.put(key, cMap);
		}
		return tMap;
		
	}
	
	public void dealCcList(Map<String, List<BeanSheet2>> map,List<BeanSheet2> sList){
		
		if(EmptyUtil.isEmptyList(sList)){
			return;
		}
		Collections.sort(sList, new Comparator<BeanSheet2>() {

			@Override
			public int compare(BeanSheet2 o1, BeanSheet2 o2) {
				return o1.getRecordTime().compareTo(o2.getRecordTime());
			}
		});
		String startTime = sList.get(0).getRecordTime();
		startTime = DateUtils.formatDate
				(DateUtils.parseDate(startTime, DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
		Date lastDate = DateUtils.addMonth(DateUtils.parseDate(startTime, DateUtils.DATE_FORMAT)
				,Integer.valueOf(ExConfig.INSTANCE.getVal("bu.outer.month")));
		String lastTime = DateUtils.formatDate(lastDate, DateUtils.DATE_FORMAT);
		List<BeanSheet2> ccList = ComplexUtil.list();
		List<BeanSheet2> oterList = ComplexUtil.list();
		for(BeanSheet2 sh:sList){
			String recordTime = sh.getRecordTime();
			if(recordTime.compareTo(startTime)>0&&recordTime.compareTo(lastTime)<0){
				ccList.add(sh);
			}else {
				oterList.add(sh);
			}
		}
		map.put(startTime+"至"+lastTime, ccList);
		
		dealCcList(map,oterList);
		
	}

}
