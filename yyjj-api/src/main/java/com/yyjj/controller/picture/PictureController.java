package com.yyjj.controller.picture;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("picture")
public class PictureController {
	@Value("${upload.fileLibraryPath}")
	String path ;
	
	@ResponseBody
	@GetMapping("image")
	public String getImage(HttpServletResponse response,HttpServletRequest request) throws IOException {
		File file = new File(path+File.separator+"images");
		File files [] = file.listFiles();
		Random random = new Random();
		int num = random.nextInt(files.length);	
		return   "images/"+(num+1)+".jpg";
	}
}
