package com.yyjj.service.system;

import java.util.List;

import com.yyjj.base.IBaseService;
import com.yyjj.model.system.Role;

/**
 * 角色
 * @author admin
 *
 */
public interface RoleService extends IBaseService<Role>{
	/**
	 * 查询用户的角色
	 * @param userId
	 * @return
	 */
	List<Role> findRoleByUserId(Integer userId);
	
	
}
