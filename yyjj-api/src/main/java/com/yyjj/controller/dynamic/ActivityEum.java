package com.yyjj.controller.dynamic;

public enum ActivityEum {
	UPLOAD("上传"),
	PUSH("推进"),
	ADD("创建"),
	FOLLOWUP("更近"),
	INITIATED("发起"),
	FILLED("填写"),
	THE("进行"),
	PUT("修改"),
	ENABLE("启用"),
	DISABLE("停用"),
	DELIVERY("交割"),
	DELETE("删除"),
	MARK("收藏");
	String name;
	
	private ActivityEum() {
		// TODO Auto-generated constructor stub
	}
	private ActivityEum(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		
		return name;
	}
}
