package com.yyjj.service.system;

import java.util.List;
import java.util.Map;

import com.yyjj.base.IBaseService;
import com.yyjj.bo.system.MenuBO;
import com.yyjj.model.system.Menu;


public interface MenuService extends IBaseService<Menu>{
	Menu getMenuDetail(Integer id);
	
	List<MenuBO> getMenuList();
	
	List<MenuBO> getMenuListByRole();
	
	void initAuthorization(Map<String, String> filterChainDefinitionMap);
}
