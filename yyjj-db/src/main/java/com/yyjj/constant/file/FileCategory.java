package com.yyjj.constant.file;

import java.io.File;

public enum FileCategory {
	
	/**
	 * 项目文件
	 * 投后文件
	 * 其他文件
	 */
	 ROOT("项目根目录",File.separator+"asset-manage"+File.separator+"project")
	,REPORT("报告管理",File.separator+"asset-manage"+File.separator+"report")
	,ICON("用户头像",File.separator+"asset-manage"+File.separator+"icon")
	,PROJECT("项目文件", File.separator+"project")
	,CAST_END("投后文件",File.separator+"cast_end")
	,OTHER("其他文件",File.separator+"other")
	,INTERVIEW_RECORD("访谈纪律",File.separator+"interview-record") 
	,BP("BP",File.separator+"bp")
	,TS_PROTOCOL("TS协议",File.separator+"ts_protocol")
	,DUE_DILIGENCE_RESOURCE("尽调资料包",File.separator+"due_diligence_resource")
	,DUE_DILIGENCE_REPORT("尽调报告",File.separator+"due_diligence_report")
	,SECRECY_PROTOCOL("保密协议",File.separator+"secrecy_protocol")
	,DELIVERY_PROTOCOL("交割协议",File.separator+"delivery_protocol")
	,MONEY_TO_PROVE("打款协议",File.separator+"money_to_prove")
	,CHANGE_PROVE("工商变更证明",File.separator+"change_prove")
	,FINANCIAL_INFORMATION("财务信息",File.separator+"financial_information")
	,FINANCIAL_STATEMENT("财务报表",File.separator+"financial_statement")	
	,PROFIT_STATEMENT("利润表",File.separator+"profit_statement") 
	,CASH_FLOW("现金流表",File.separator+"cash_flow")
	,BALANCE_SHEET("资产负债表",File.separator+"balance_sheet") 
	;
	
	
	private String name;
	private String path;
	private FileCategory(String name,String path) {
		
		this.name = name;
		this.path = path;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
