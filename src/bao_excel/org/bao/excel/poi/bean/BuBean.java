package bao_excel.org.bao.excel.poi.bean;

import java.util.List;
import java.util.Map;

import util.ComplexUtil;


public class BuBean {
	public static final String tpl = "bu_template.xlsx";
	
	public List<PointBean> firstlist = ComplexUtil.list();
	
	public Map<String, List<BeanSheet2>> caseMap = ComplexUtil.map();
	
	public List<BeanSheet2> secondlist = ComplexUtil.list();
	
	public BuBean(){
		
		firstlist.add(new PointBean(12,0,"name","姓名"));
		firstlist.add(new PointBean( 12,0,"name","姓名"));
		firstlist.add(new PointBean( 12,0,"name","姓名"));
		
	}
	
}
