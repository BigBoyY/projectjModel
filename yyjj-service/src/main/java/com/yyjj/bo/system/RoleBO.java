package com.yyjj.bo.system;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyjj.bo.base.IBaseBO;
import com.yyjj.model.system.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleBO implements IBaseBO<Role>{
	  private String roleCode;
	
	  private String roleName;
	
	@Override
	public void accpet(QueryWrapper<Role> queryWrapper) {
		if(! StringUtils.isEmpty(roleCode)) {
			queryWrapper.lambda().like(Role::getRoleCode, roleCode);
		}
		
		if(! StringUtils.isEmpty(roleName)) {
			queryWrapper.lambda().like(Role::getRoleName, roleName);
		}
	}

}
