package com.yyjj.constant.file;

import java.io.File;

public class FileConstant {
	
	/**
	 * 路径
	 * @author admin
	 *
	 */
	public static class PathConstant{
		/**
		 * 斜杠
		 */
		public static final String SPRIT = File.separator;
		
		/**
		 * 文件根目录
		 */
		public static final String FILE_ROOT_PATH = "${upload.visitPath}";
		
		/**
		 * 根目录父ID
		 */
		public static final Integer ROOT_NUM = 0;
	
		/**
		 * 一季度
		 */
		public static final String Q1 = "Q1" ;
		
		/**
		 * 二季度
		 */
		public static final String Q2 = "Q2" ;
		
		/**
		 * 三季度
		 */
		public static final String Q3 = "Q3" ;
		
		/**
		 * 4季度
		 */
		public static final String Q4 = "Q4" ;
		
		/**
		 * 年报
		 */
		public static final String YEAY = "annals";
		
		/**
		 * 财报其他文件
		 * 
		 */
		public static final  Integer OTHER = 5;
 	}
	
	/**
	 * 文件下载
	 * @author admin
	 *
	 */
	public static class FileDownloadConstant{
		/**
		 * 访问下载映射
		 */
		public static final String FILE = "file";
		
		public static final String DOWNLOAD = "download";
		
		/**
		 * 下载头
		 */
		public static final String FILE_DOWNLOAD_HEAD = "Content-Disposition";
		
		/**
		 * 下载头值
		 */
		public static final String FILE_DOWNLOAD_HEAD_VALUE = "attachment;filename=";
		
		/**
		 * 文件类型
		 */
		public static final String FILE_DOWNLOAD_TYPE = "multipart/form-data";
		
		
		public static final String FILE_DOWNLOAD_ENCODE = "UTF-8";
	}
	
	/**
	 * 文件上传
	 * @author admin
	 *
	 */
	public static class FileUploadConstant{
		/**
		 * 立项报告
		 */
		//public static final Integer APPROVAL=100 ;
		
		/**
		 * 访谈纪律
		 */
		public static final Integer INTERVIEW_RECORD=200;
		
		public static final Integer BP = 100;
		/**
		 * TS
		 */
		public static final Integer TS=300;
		
		/**
		 * 保密协议
		 */
		public static final Integer SECRECY_PROTOCOL=400;
		
		/***
		 * 尽调资料包
		 */
		public static final Integer DUE_DILIGENCE_RESOURCE =500;
		
		/**
		 * 尽调报告
		 */
		public static final Integer DUE_DILIGENCE_REPORT =600;
		
		/**
		 * 交割协议
		 */
		public static final Integer SETTLEMENT_AGREEMENT = 700;
		
		/**
		 * 打款证明
		 */
		public static final Integer REMIT_PROVE = 800;
		
		/**
		 * 工商变更证明
		 */
		public static final Integer CHANGE_PROVE = 900;
		
		/**
		 * 其他
		 */
		public static final Integer OTHER = 1000;
		
		/**
		 * 财务信息
		 */
		public static final Integer FINANCE = 2000;
		
		/**
		 * 财务报表
		 */
		public static final  Integer STATEMENT = 3000;
		
		/**
		 * 头像
		 */
		public static final Integer ICON = 4000;
	//	public static 
	}
}
