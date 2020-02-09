package com.yyjj;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 资源路径
	 */
	private String path;
	
	/**
	 * 网络url
	 */
	private String url;
}
