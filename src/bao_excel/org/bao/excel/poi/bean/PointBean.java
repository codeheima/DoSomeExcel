package bao_excel.org.bao.excel.poi.bean;

public class PointBean {

	
	public int row;
	public int cell;
	//
	public String code;
	
	public String des;
	
	public PointBean(int row, int cell) {
		super();
		this.row = row;
		this.cell = cell;
	}

	public PointBean( int row, int cell, String code, String des) {
		super();
	
		this.row = row;
		this.cell = cell;
		this.code = code;
		this.des = des;
	}
	
	
	
}
