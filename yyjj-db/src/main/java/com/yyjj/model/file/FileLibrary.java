package com.yyjj.model.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 文件库表
 * </p>
 *
 * @author yml
 * @since 2019-12-26
 */
public class FileLibrary implements Serializable {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
  
    public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDownUrl() {
        return fileDownUrl;
    }

    public void setFileDownUrl(String fileDownUrl) {
        this.fileDownUrl = fileDownUrl;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }
    public String getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    
    public FileLibrary() {
		super();
	}

	
	public FileLibrary(Integer projectId,Integer parentId, Long size, String fileName,String filePath,String trueName) {
		super();
		this.parentId = parentId;
		this.projectId = projectId;
		this.fileSize = size;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileStorage = trueName;
	}
	
	public FileLibrary(Long size, String fileName,String filePath,String trueName) {
		super();
		this.fileSize = size;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileStorage = trueName;
	}
	public FileLibrary(Integer projectId, Integer parentId, String fileName, String filePath) {
		super();
		this.parentId = parentId;
		this.projectId = projectId;
		this.filePath = filePath;
		this.fileName = fileName;
	}

	@Override
    public String toString() {
        return "FileLibrary{" +
            "id=" + id +
            ", projectId=" + projectId +
            ", parentId=" + parentId +
            ", uploadTime=" + uploadTime +
            ", fileSize=" + fileSize +
            ", fileDownUrl=" + fileDownUrl +
            ", filePath=" + filePath +
            ", fileType=" + fileType +
            ", fileStorage=" + fileStorage +
            ", fileName=" + fileName +
            ", createTime=" + createTime +
            ", createUser=" + createUser +
            ", updateTime=" + updateTime +
            ", sort=" + sort +
            ", updateUser=" + updateUser +
        "}";
    }
}
