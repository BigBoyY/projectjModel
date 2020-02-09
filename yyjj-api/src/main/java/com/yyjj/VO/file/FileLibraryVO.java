package com.yyjj.VO.file;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yyjj.bo.file.FileLibraryBO;
import com.yyjj.model.file.FileLibrary;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FileLibraryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    /**
     * 文件库表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 父id（0=跟目录）
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private LocalDateTime uploadTime;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件下载/查看路径
     */
    private String fileDownUrl;

    /**
     * 文件目录
     */
    private String filePath;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 文件仓库名
     */
    private String fileStorage;

    /**
     * 文件真实名
     */
    private String fileName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 更新人
     */
    private String updateUser;
    
    
	/**
	 * 子目录或文件集合
	 */
	List<FileLibraryVO> subFileLibrarys;  
	
	
	public static FileLibraryVO newInstance(FileLibrary fileLibrary) {
        if(Objects.isNull(fileLibrary)) {
  	    return null;
  	  }        
        FileLibraryVO fileLibraryVO = new FileLibraryVO();
        BeanUtils.copyProperties(fileLibrary, fileLibraryVO);
        return fileLibraryVO;
  	}
    
   public static FileLibraryVO newInstance(FileLibraryBO bo) {
	   if(Objects.isNull(bo)) {
	  	    return null;
	  	  }        
	   FileLibraryVO fileLibraryVO = new FileLibraryVO();
	   fileLibraryVO.setSubFileLibrarys(recursionConvert(bo.getSubFileLibraryBOs()));
	   return fileLibraryVO;
   }
  	    
  	public FileLibrary convert() {
  		FileLibrary fileLibrary = new FileLibrary();
  	  BeanUtils.copyProperties(this, fileLibrary);
  	  return fileLibrary;
  	}
  	
  	private static  List<FileLibraryVO> recursionConvert(List<FileLibraryBO> bos){
  		  	List<FileLibraryVO> vos = new ArrayList<FileLibraryVO>();
  		  	if(CollectionUtils.isEmpty(bos)) {
  		  		return vos;
  		  	}
  			for (FileLibraryBO fileLibraryBO : bos) {
  				FileLibraryVO vo = new FileLibraryVO();
  				BeanUtils.copyProperties(fileLibraryBO, vo);				
  				vo.setSubFileLibrarys(recursionConvert(fileLibraryBO.getSubFileLibraryBOs()));
  				vos.add(vo);
  			}
  			return vos;
  		  
  	}
}
