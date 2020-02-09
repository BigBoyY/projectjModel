package com.yyjj.bo.system;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyjj.bo.base.IBaseBO;
import com.yyjj.model.system.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBO implements IBaseBO<User>{
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	
	
	@Override
	public void accpet(QueryWrapper<User> queryWrapper) {
		queryWrapper.lambda().eq(User::getUserStatus, true);
		if(! StringUtils.isEmpty(userName)) {
			queryWrapper.lambda().like(User::getUserName, userName);
		}
		
		if(userId != null &&userId != 0) {
			queryWrapper.lambda().like(User::getId, userId);

		}
	}
	
}
