package com.yyjj.file.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.dao.file.FileLibraryMapper;
import com.yyjj.file.FileLibraryService;
import com.yyjj.model.file.FileLibrary;

/**
 * 文件库
 * @author yml
 *
 */
@Service
public class FileLibraryServiceImpl extends ServiceImpl<FileLibraryMapper, FileLibrary>
implements FileLibraryService
{

	@Override
	public FileLibrary getFileLibraryByProject(Integer projectId, String oneLevel, String twoLevel) {
		FileLibrary oneLevelFile= this.lambdaQuery().eq(FileLibrary::getProjectId, projectId)
			.eq(FileLibrary::getFileName, oneLevel).one();
		FileLibrary twoLevelFile= this.lambdaQuery().eq(FileLibrary::getParentId, oneLevelFile.getId())
								  	.eq(FileLibrary::getFileName, twoLevel).one();
		return twoLevelFile;
	}

}
