package com.yyjj.exception;

public class CodeRepeatErrorException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String message;

    public CodeRepeatErrorException() {}
    
    public CodeRepeatErrorException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
