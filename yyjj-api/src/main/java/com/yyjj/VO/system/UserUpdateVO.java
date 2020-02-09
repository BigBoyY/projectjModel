package com.yyjj.VO.system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Positive;

import org.springframework.beans.BeanUtils;

import com.yyjj.model.system.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateVO implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /**
     * 用户id
     */
	@Positive
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
     * 图片存储路径
     */
    private String userIcon;

    /**
     * 用户账号
     */
    
    private String userAccount;
    
    /**
     * 新密码
     */
    private String newPassword;
    
    /**
     * 用户密码
     */
    private String password;
    
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
     * 权限
     */
    private List<RoleVO> roles;
    public static UserVO newInstance(User user) {
        if(Objects.isNull(user)) {
  	    return null;
  	  }        
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
  	}
    
    public static UserVO newInstance(UserDetail user) {
        if(Objects.isNull(user)) {
  	    return null;
  	  }        
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
  	}
  	    
  	public User convert() {
  		User user = new User();
  	  BeanUtils.copyProperties(this, user);
  	  return user;
  	}
}
