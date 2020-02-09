package com.yyjj.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import com.yyjj.exception.CodeRepeatErrorException;
import com.yyjj.exception.HttpParamErrorException;
import com.yyjj.exception.LoginErrorException;
import com.yyjj.exception.RemoveCollideErrorException;
import com.yyjj.exception.TypeNotFoundException;
import com.yyjj.exception.UniqueViolationException;
import com.yyjj.validator.ValidationException;

@ControllerAdvice
public class SystemErrorController {

    private final static Logger logger = LoggerFactory.getLogger(SystemErrorController.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void errorHandler(Exception ex) {
        logger.error("系统异常: {}", ex);
    }
    
    @ResponseBody
    @ExceptionHandler(value = UnknownSessionException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void errorHandler(UnknownSessionException ex) {
        logger.error("请登录", ex);
    }
    
    
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errlist = new ArrayList<String>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errlist.add(error.getDefaultMessage());
            }
        }
        logger.warn("参数校验错误: {}", ex);
        return errlist;
    }
    
    
    
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> bindExceptionHandler(BindException ex) {
        List<String> errlist = new ArrayList<String>();
        if (ex.hasErrors()) {
            for (ObjectError error : ex.getAllErrors()) {
                errlist.add(error.getDefaultMessage());
            }
        }
        logger.warn("请求参数异常: {}", ex);
        return errlist;
    }

    @ResponseBody
    @ExceptionHandler(value = HttpParamErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String httpParamErrorExceptionHandler(HttpServletRequest request, HttpParamErrorException ex) {
        setRequestUtf8(request);
        logger.warn("请求参数异常: {}", ex);
        return ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String httpRequestMethodNotSupportedException(HttpServletRequest request,
            HttpRequestMethodNotSupportedException ex) {
        
        setRequestUtf8(request);
        logger.warn("系统异常: {}", ex);
        String errorMsg = "接口请求类型有误哦, 请尝试其他请求类型[GET, POST, PUT, DELETE...]";
        return errorMsg;
    }

    /**
     * 参数校验错误
     * @param request
     * @param ex
     * @return
     */
	@ResponseBody
	@ExceptionHandler(value = ValidationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String validatorHandler(HttpServletRequest request, Exception ex) {
		setRequestUtf8(request);
		logger.warn("参数校验错误: {}", ex.getMessage());
		return ex.getMessage();
	}
	

    /**
     * 登录异常
     * @param request
     * @param ex
     * @return
     */
	@ResponseBody
	@ExceptionHandler(value = LoginErrorException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String LoginHandler(HttpServletRequest request, LoginErrorException ex) {
		setRequestUtf8(request);
		logger.warn("登录异常: {}", ex.getMessage());
		return ex.getMessage();
	}
	

    /**
     * 将返回设为utf-8编码
     * 
     * @param request
     */
    private void setRequestUtf8(HttpServletRequest request) {
        Set<MediaType> mediaTypeSet = new HashSet<>();
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        mediaTypeSet.add(mediaType);
        request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, mediaTypeSet);
    }
   
    
	/**
	 * 编号重复异常 
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = CodeRepeatErrorException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String CodeRepeatErrorExceptionHandler(HttpServletRequest request, CodeRepeatErrorException ex) {
		setRequestUtf8(request);
		logger.warn(ex.getMessage());
		return ex.getMessage();
	}
	
	/**
	 * 违反唯一性异常 
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = UniqueViolationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String UniqueViolationExceptionHandler(HttpServletRequest request, UniqueViolationException ex) {
		setRequestUtf8(request);
		logger.warn(ex.getMessage());
		return ex.getMessage();
	}

	/**
	 * 删除异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = RemoveCollideErrorException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String RemoveCollideErrorExceptionHandler(HttpServletRequest request, RemoveCollideErrorException ex) {
		setRequestUtf8(request);
		logger.warn(ex.getMessage());
		return ex.getMessage();
	}
	
	/**
	 * 找不到类型异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = TypeNotFoundException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String TypeNotFoundExceptionHandler(HttpServletRequest request, TypeNotFoundException ex) {
		setRequestUtf8(request);
		logger.warn(ex.getMessage());
		return ex.getMessage();
	}

}
