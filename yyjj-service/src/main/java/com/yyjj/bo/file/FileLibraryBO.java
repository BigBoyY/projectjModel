package com.yyjj.bo.file;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.yyjj.model.file.FileLibrary;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class FileLibraryBO extends FileLibrary{
	List<FileLibraryBO> subFileLibraryBOs;  
	
	public static FileLibraryBO newInstance(FileLibrary fileLibrary) {
        if(Objects.isNull(fileLibrary)) {
  	    return null;
  	  }        
        FileLibraryBO fileLibraryVO = new FileLibraryBO();
        BeanUtils.copyProperties(fileLibrary, fileLibraryVO);
        return fileLibraryVO;
  	}
    
  	public FileLibrary convert() {
  		FileLibrary fileLibrary = new FileLibrary();
  	  BeanUtils.copyProperties(this, fileLibrary);
  	  return fileLibrary;
  	}
}
