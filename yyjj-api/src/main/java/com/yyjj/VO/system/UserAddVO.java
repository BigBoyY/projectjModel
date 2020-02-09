package com.yyjj.VO.system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yyjj.model.system.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddVO implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    
    private String userName;
    /**
     * 用户状态
     */
    private Boolean userStatus;
    /**
     * 登陆结果
     */
    private String anser;
    /**
     * 密码
     */
    private String password;
    /**
     * 图片存储路径
     */
    private String userIcon;

    /**
     * 用户账号
     */
    @NotBlank
    private String userAccount;
    
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 启停用
     */
    private Boolean enable;

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
     * 更新人
     */
    private String updateUser;
    
    /**
     * sessionId
     */
    private Serializable sessionId;
    
    /**
     * 权限
     */
    private List<RoleVO> roles;
    public static UserAddVO newInstance(User user) {
        if(Objects.isNull(user)) {
  	    return null;
  	  }        
        UserAddVO userAddVO = new UserAddVO();
        BeanUtils.copyProperties(user, userAddVO);
        return userAddVO;
  	}
    
    public static UserAddVO newInstance(UserDetail user) {
        if(Objects.isNull(user)) {
  	    return null;
  	  }        
        UserAddVO userAddVO = new UserAddVO();
        BeanUtils.copyProperties(user, userAddVO);
        return userAddVO;
  	}
  	    
  	public User convert() {
  		User user = new User();
  	  BeanUtils.copyProperties(this, user);
  	  return user;
  	}
}
