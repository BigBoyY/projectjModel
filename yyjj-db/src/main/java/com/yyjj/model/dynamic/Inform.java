package com.yyjj.model.dynamic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yml
 * @since 2020-01-16
 */
public class Inform implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动类型
     */
    private String activity;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 明细
     */
    private String detail;

    /**
     * 消息主体
     */
    private String message;
    
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 消息类型 0：动态 1：重要
     */
    private Integer messageType;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户头像
     */
    private String userIcon;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

	@Override
	public String toString() {
		return "Inform : {id=" + id + ", activity=" + activity + ", method=" + method + ", type=" + type + ", detail="
				+ detail + ", message=" + message + ", userName=" + userName + ", messageType=" + messageType
				+ ", userId=" + userId + ", createTime=" + createTime + ", userIcon=" + userIcon + "}";
	}
  
	

	
}
