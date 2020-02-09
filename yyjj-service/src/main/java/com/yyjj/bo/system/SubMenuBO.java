package com.yyjj.bo.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyjj.bo.base.IBaseBO;
import com.yyjj.model.system.SubMenu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubMenuBO implements IBaseBO<SubMenu>{
	
	@Override
	public void accpet(QueryWrapper<SubMenu> queryWrapper) {
		
	}
	
}
    