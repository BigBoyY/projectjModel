package com.yyjj.VO.system;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Positive;

import org.springframework.beans.BeanUtils;

import com.yyjj.model.system.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleVO implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 角色id
     */
	@Positive(message = "必须要有角色id")
    private Integer id;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 描述
     */
    private String remark;
    
    /**
     * 停启用
     */
    private Boolean enable;
    
    /**
     * 菜单ids
     */
    private List<MenuVO>  menus;
    
    public static RoleVO newInstance(Role role) {
        if(Objects.isNull(role)) {
  	    return null;
  	  }        
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role, roleVO);
        return roleVO;
  	}
    
   
  	    
  	public Role convert() {
  		Role role = new Role();
  	  BeanUtils.copyProperties(this, role);
  	  return role;
  	}
}
