package com.yyjj.file;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.yyjj.bo.file.FileLibraryBO;
import com.yyjj.constant.file.FileCategory;
import com.yyjj.model.file.FileLibrary;

public interface FileService {

	Long upload(String path,String content) throws Exception;
	
	void  download(String path,OutputStream out) throws Exception;
	
	Boolean removeFileLibrary(Integer projectId);
		
	Boolean removeFile(String  filePath);
	
	public FileLibrary upload(MultipartFile file,Integer fileLibraryId,Integer type) throws Exception;
	
	public FileLibrary uploadIcon(MultipartFile file,Integer type) throws Exception;
	
	public FileLibrary upload(MultipartFile file,Integer type,FileCategory fileCategory) throws Exception;
	
	public FileLibrary initFileLibrary(Integer projectId);
	
	public FileLibrary initCastEndMkdir(Integer projectId,Integer afterProjectId);
	FileLibraryBO fileLibraryDetail(Integer projectId);
}
