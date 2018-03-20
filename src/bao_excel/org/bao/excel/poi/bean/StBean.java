package bao_excel.org.bao.excel.poi.bean;

import java.util.List;
import java.util.Map;

import util.ComplexUtil;

public class StBean {
	public static final String tpl = "st_template.xlsx";
	
	public List<PointBean> firstlist = ComplexUtil.list();
	
	public List<BeanSheet2> secondlist = ComplexUtil.list();
	
	public Map<String, List<BeanSheet2>> caseMap = ComplexUtil.map();
}
