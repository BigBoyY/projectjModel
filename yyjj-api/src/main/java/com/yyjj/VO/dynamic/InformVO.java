package com.yyjj.VO.dynamic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yyjj.bo.dynamic.InformBO;
import com.yyjj.model.dynamic.Inform;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author yml
 * 
 */
@Getter
@Setter
public class InformVO implements Serializable {
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
    private static final long serialVersionUID = 1L;
    
     
    public static  InformVO newInstance( Inform  inform) {
        if(Objects.isNull( inform)) {
  	    return null;
  	  }        
         InformVO  informVO = new  InformVO();
        BeanUtils.copyProperties( inform,  informVO);
        return  informVO;
  	}
    
    public static  InformVO newInstance( InformBO  inform) {
        if(Objects.isNull( inform)) {
  	    return null;
  	  }        
         InformVO  informVO = new  InformVO();
        BeanUtils.copyProperties( inform,  informVO);
        return  informVO;
  	}
  	    
  	public  Inform convert() {
  		 Inform  inform = new  Inform();
  	  BeanUtils.copyProperties(this,  inform);
  	  return  inform;
  	}
  	
  	public  InformBO  InformBO() {
  		 InformBO  inform = new  InformBO();
  	  BeanUtils.copyProperties(this,  inform);
  	  return  inform;
  	}

	@Override
	public String toString() {
		return "Inform : {id=" + id + ", activity=" + activity + ", method=" + method + ", type=" + type + ", detail="
				+ detail + ", message=" + message + ", userName=" + userName + ", messageType=" + messageType
				+ ", userId=" + userId + ", createTime=" + createTime + ", userIcon=" + userIcon + "}";
	}
  	
  }