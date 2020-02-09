package com.yyjj.exception;

/**
 * 违反唯一约束错误
 * @author yml
 *
 */
public class UniqueViolationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

    public UniqueViolationException() {}
    
    public UniqueViolationException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
