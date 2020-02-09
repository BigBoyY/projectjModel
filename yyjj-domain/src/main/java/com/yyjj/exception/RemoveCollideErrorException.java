package com.yyjj.exception;

public class RemoveCollideErrorException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
    
    private String message;

    public RemoveCollideErrorException() {}
    
    public RemoveCollideErrorException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
