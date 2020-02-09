package com.yyjj.service.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.dao.system.UserRoleMapper;
import com.yyjj.model.system.Role;
import com.yyjj.model.system.UserRole;
import com.yyjj.service.system.UserRoleService;

/**
 * 用户角色
 * @author admin
 *
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService{

	@Override
	public void changeUserRole(Integer userId, List<Role> roles) {
		List<Integer> userRoleList = lambdaQuery()
		.eq(UserRole::getUserId, userId).list()
		.stream().map(UserRole::getId).collect(Collectors.toList());
		if(! CollectionUtils.isEmpty(userRoleList)) {
		removeByIds(
				userRoleList
				);
		}
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = null;
		for (Role role : roles) {
			userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(role.getId());
			userRoles.add(userRole);
		}
		saveBatch(userRoles);
	}
}
