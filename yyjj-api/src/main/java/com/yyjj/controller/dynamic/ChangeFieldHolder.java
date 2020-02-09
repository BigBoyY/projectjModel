package com.yyjj.controller.dynamic;

import java.util.Objects;

import com.yyjj.model.dynamic.ChangeField;

public class ChangeFieldHolder {
	private static final ThreadLocal<ChangeField> ChangeFieldHolder = new ThreadLocal<>();
 	public static ChangeField getChangeField() {
        ChangeField changeField = ChangeFieldHolder.get();	 
        
        if(Objects.isNull(changeField)) {
        	return new ChangeField();
        }
        return changeField;
    }
    
    public static void setChangeField(ChangeField changeField) {
    	ChangeFieldHolder.set(changeField);
    }
    
    public static void removeChangeField() {
    	ChangeFieldHolder.remove(); 
    }
}
