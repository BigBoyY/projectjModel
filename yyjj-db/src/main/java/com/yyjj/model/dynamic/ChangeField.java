package com.yyjj.model.dynamic;

public class ChangeField {
	private Object changeBefor;
	
	private Object changeAfter;

	public Object getChangeBefor() {
		return changeBefor;
	}

	public void setChangeBefor(Object changeBefor) {
		this.changeBefor = changeBefor;
	}

	public Object getChangeAfter() {
		return changeAfter;
	}

	public void setChangeAfter(Object changeAfter) {
		this.changeAfter = changeAfter;
	}

	@Override
	public String toString() {
		return "ChangeField [changeBefor=" + changeBefor + ", changeAfter=" + changeAfter + "]";
	}
	
	
}
