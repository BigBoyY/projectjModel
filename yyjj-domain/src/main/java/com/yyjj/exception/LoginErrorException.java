package com.yyjj.exception;

public class LoginErrorException extends RuntimeException{
	 private static final long serialVersionUID = 1L;
	    
	    private String message;

	    public LoginErrorException() {}
	    
	    public LoginErrorException(String message) {
	        this.message = message;
	    }
	    
	    @Override
	    public String getMessage() {
	        return message;
	    }
}
