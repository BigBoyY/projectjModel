package com.yyjj.file;

import com.yyjj.base.IBaseService;
import com.yyjj.model.file.FileLibrary;

/**
 * 文件库
 * @author admin
 *
 */
public interface FileLibraryService extends IBaseService<FileLibrary>{
	
	public FileLibrary getFileLibraryByProject(Integer projectId,String oneLevel,String twoLevel);
}
