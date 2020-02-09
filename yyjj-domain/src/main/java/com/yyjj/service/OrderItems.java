package com.yyjj.service;

public class OrderItems{
	 /**
     * 需要进行排序的字段
     * 
     */
    private String column ;

    /**
     * 是否正序排列，默认 false,
     */
    private boolean asc = false;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	public OrderItems(String column, boolean asc) {
		super();
		this.column = column;
		this.asc = asc;
	}
	
	public OrderItems(String column) {
		super();
		this.column = column;
	}

	@Override
	public String toString() {
		return "OrderItems [column=" + column + ", asc=" + asc + "]";
	}
    
}
