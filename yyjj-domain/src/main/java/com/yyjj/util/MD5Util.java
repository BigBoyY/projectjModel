package com.yyjj.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String getMD5String(String str){
		//MD5加密
				MessageDigest md = null;
					try {
						md = MessageDigest.getInstance("MD5");
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}     
		        md.update(str.getBytes());      
		      return  new BigInteger(1, md.digest()).toString(16);  
		        
	}
}
