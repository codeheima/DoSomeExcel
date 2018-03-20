package bao_excel.org.bao.excel.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bao_excel.org.bao.excel.memory.MainMemory;

public class PoiUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
	//	testWrite();
		testRead();
	}
	
	
	
	private static void testRead()throws Exception {
		File file = new File(MainMemory.getPath("st_template.xlsx"));
		FileInputStream is = new FileInputStream(file);
		String fileName =file.getName();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		
		
		xssfWorkbook.cloneSheet(1);
		
		
		XSSFSheet sheet = xssfWorkbook.getSheetAt(1);
		
		int current = 4;
		
		XSSFCellStyle style = getStyle(sheet,4,1);
		
		String ss = getCellVal(sheet,3,0);
		System.out.println(ss);
		
		XSSFRow rowNew = createRow(sheet,current);
		
		for(int i = 1; i<=3; i++ ){
			Cell c = rowNew.createCell(i);
			c.setCellStyle(style);
			
		}
		unionArea(sheet,3,5,0,0);
		
		
		SXSSFWorkbook sWorkBook = new SXSSFWorkbook(xssfWorkbook,100);
		
		doOutFile(sWorkBook,"E:/pro/pro/abc2.xlsx");
		
		
		
		
	}
	public static XSSFCellStyle getStyle(XSSFSheet sheet, Integer rowIndex,int celIndex){
		

		XSSFRow oldRow = sheet.getRow(rowIndex);
		XSSFCell c  =oldRow.getCell(celIndex);
		XSSFCellStyle  style = c.getCellStyle();
		
		return style;
	}
	
	
	
	/**
	 * 鍚堝苟琛�
	 * @param sheet
	 * @param firstRow
	 * @param LastRow
	 * @param firstCell
	 * @param lastCell
	 */
	public static void unionArea(Sheet sheet,int firstRow,int LastRow,int firstCell,int lastCell){
		CellRangeAddress cra=new CellRangeAddress(firstRow, LastRow, firstCell, lastCell);        
		sheet.addMergedRegion(cra);  
	}
	
	public static XSSFRow createRow(XSSFSheet sheet, Integer rowIndex) {  
        XSSFRow row = null;  
        if (sheet.getRow(rowIndex) != null) {  
            int lastRowNo = sheet.getLastRowNum();  
            sheet.shiftRows(rowIndex, lastRowNo, 1);  
        }  
        row = sheet.createRow(rowIndex);  
        return row;  
    }  
	
	/**
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param colIndex
	 * @param newVal
	 */
	public static void changeCell(Sheet sheet , int rowIndex ,int colIndex ,String newVal){
		Row a = sheet.getRow(rowIndex);
		Cell cell = a.getCell(colIndex);
		String s = cell.getStringCellValue();
		s = s.replace(s, newVal);
		cell.setCellValue(s);
	}
	
	public static String getCellVal(Sheet sheet , int rowIndex ,int colIndex ){
		Row a = sheet.getRow(rowIndex);
		Cell cell = a.getCell(colIndex);
		String s = cell.getStringCellValue();
		return s;
	}



	private static void testWrite()throws Exception {
		FileOutputStream out = new FileOutputStream(new File("E:/pro/pro/abc.xlsx"));
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		Sheet sheet = workbook.createSheet("sheet0");
		int rowNum = 0;
		Row row = sheet.createRow(rowNum);
		for(int cellNum=0;cellNum<5;cellNum++){
			Cell cell = row.createCell(cellNum);
			cell.setCellValue("hahaa");
		}
		rowNum++;
		
		workbook.write(out);
		out.flush();
		out.close();
	}



	public static void doOutFile(SXSSFWorkbook workbook,String pathName)  {
		try{
			FileOutputStream out = new FileOutputStream(new File(pathName));
			workbook.write(out);
			out.flush();
			out.close();
		}catch(Exception err){
			
			throw new RuntimeException(err);
			
		}
	}
	public  static String getValue(HSSFCell xssfRow) {

	     if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(xssfRow.getBooleanCellValue());
	     } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
	            return String.valueOf(xssfRow.getNumericCellValue());
	     } else {
	            return String.valueOf(xssfRow.getStringCellValue());
	     }
	}
	
	public static String getValue(XSSFCell xssfRow) {

		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

}
