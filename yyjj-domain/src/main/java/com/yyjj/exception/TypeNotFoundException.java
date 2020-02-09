package com.yyjj.exception;

/**
 * 找不到类型异常
 * @author admin
 *
 */
public class TypeNotFoundException extends RuntimeException{
private static final long serialVersionUID = 1L;
	
	private String message;

    public TypeNotFoundException() {}
    
    public TypeNotFoundException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}	
