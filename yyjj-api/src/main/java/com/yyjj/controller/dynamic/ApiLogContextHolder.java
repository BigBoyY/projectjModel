package com.yyjj.controller.dynamic;

import java.util.Objects;

import com.yyjj.model.dynamic.Inform;

public class ApiLogContextHolder {
	 private static final ThreadLocal<Inform> InformHolder = new ThreadLocal<>();
	 	public static Inform getInform() {
	        Inform inform = InformHolder.get();	 
	        if(Objects.isNull(inform)) {
	        	return new Inform();
	        }
	        return inform;
	    }
	    
	    public static void setInform(Inform inform) {
	    	InformHolder.set(inform);
	    }
	    
	    public static void removeInform() {
	    	InformHolder.remove(); 
	    }
	    
}
