package com.yyjj.exception;

public class HttpParamErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private String message;

    public HttpParamErrorException() {}
    
    public HttpParamErrorException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

}
