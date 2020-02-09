package com.yyjj.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.dao.system.RoleMapper;
import com.yyjj.model.system.Role;
import com.yyjj.service.system.RoleService;

/***
 * 角色
 * @author admin
 *
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{
	
	
	@Override
	public List<Role> findRoleByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
