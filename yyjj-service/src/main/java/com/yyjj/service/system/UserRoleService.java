package com.yyjj.service.system;

import java.util.List;

import com.yyjj.base.IBaseService;
import com.yyjj.model.system.Role;
import com.yyjj.model.system.UserRole;

/**
 * 用户和角色
 * @author admin
 *
 */
public interface UserRoleService extends IBaseService<UserRole>{
	void changeUserRole(Integer userId,List<Role> roles);
}
