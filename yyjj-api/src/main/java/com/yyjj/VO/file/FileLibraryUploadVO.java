package com.yyjj.VO.file;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yyjj.model.file.FileLibrary;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FileLibraryUploadVO implements Serializable{
	
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
	
	public static FileLibraryUploadVO newInstance(FileLibrary fileLibrary) {
        if(Objects.isNull(fileLibrary)) {
  	    return null;
  	  }        
        FileLibraryUploadVO fileLibraryVO = new FileLibraryUploadVO();
        BeanUtils.copyProperties(fileLibrary, fileLibraryVO);
        return fileLibraryVO;
  	}
    
   
  	    
  	public FileLibrary convert() {
  		FileLibrary fileLibrary = new FileLibrary();
  	  BeanUtils.copyProperties(this, fileLibrary);
  	  return fileLibrary;
  	}
}
