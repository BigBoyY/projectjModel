package com.yyjj.service.system.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.bo.system.MenuBO;
import com.yyjj.dao.system.MenuMapper;
import com.yyjj.model.system.Menu;
import com.yyjj.model.system.Role;
import com.yyjj.model.system.RoleMenu;
import com.yyjj.model.system.SubMenu;
import com.yyjj.service.system.MenuService;
import com.yyjj.service.system.RoleMenuService;
import com.yyjj.service.system.RoleService;
import com.yyjj.service.system.SubMenuService;
import com.yyjj.service.system.TreeUtil;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
	private static final String NO_ACCESS = "perms[noAccess]";
	@Autowired
	private RoleMenuService roleMenuService;

	@Autowired
	SubMenuService subMenuService;
	@Autowired
	private RoleService roleService;

	@Override
	public Menu getMenuDetail(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuBO> getMenuList() {
		List<MenuBO> list = list().stream().map(MenuBO::newInstance).collect(Collectors.toList());

		return TreeUtil.getParentMenu(list);
	}

	@Override
	public List<MenuBO> getMenuListByRole() {
		List<MenuBO> list = list().stream().map(MenuBO::newInstance).collect(Collectors.toList());
		return TreeUtil.getParentMenu(list);
	}

	@Override
	public void initAuthorization(Map<String, String> filterChainDefinitionMap) {
		List<Menu> menus = lambdaQuery().ne(Menu::getParentId, 0).list();
		
		for (Menu menu : menus) {
			//System.out.println(menu.getMenuName());
			List<RoleMenu> roleMenus = roleMenuService.lambdaQuery().eq(RoleMenu::getMenuId, menu.getId()).list();
			
			Set<String> roleSet = new HashSet<>();
			Map<String, Set<String>> roleMap = new HashMap<>();
			Boolean HasRole = false;
			if (!CollectionUtils.isEmpty(roleMenus)) {
				for (RoleMenu roleMenu : roleMenus) {
					Role role = roleService.getById(roleMenu.getRoleId());
					
					if (role.getEnable()) {
						roleSet.add(role.getRoleName());
						HasRole = true;
					}
				}
				if (HasRole) {
					
					//System.out.println(stringBuilder);
					List<SubMenu> subMenus = subMenuService.lambdaQuery().eq(SubMenu::getMenuId, menu.getId()).list();
					if (!CollectionUtils.isEmpty(subMenus)) {
						for (SubMenu subMenu : subMenus) {
							//System.out.println(subMenu.getUrl() + stringBuilder);
							//System.out.println("" subMenu.getUrl() + "==" );
							String url = subMenu.getUrl() + "==" ;
							String oldUrl = filterChainDefinitionMap.get(url);
							if(! StringUtils.isEmpty(oldUrl)) {
								Set<String> oldRoles = roleMap.get(url);
								if(! CollectionUtils.isEmpty(oldRoles)) {
									roleSet.addAll(oldRoles);				
								}
							}
							roleMap.put(url, roleSet);
							StringBuilder stringBuilder = new StringBuilder("authc,perms[");
							for (String str : roleSet) {
								stringBuilder.append(str+",");
							}							
							stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("]");
							filterChainDefinitionMap.put(url+subMenu.getMethod(),
									stringBuilder.toString());
						}
					}
				}
			} else {
				List<SubMenu> subMenus = subMenuService.lambdaQuery().eq(SubMenu::getMenuId, menu.getId()).list();
				if (!CollectionUtils.isEmpty(subMenus)) {
					for (SubMenu subMenu : subMenus) {
						filterChainDefinitionMap.put(subMenu.getUrl() + "==" + subMenu.getMethod(), NO_ACCESS);
					}
				}
			}
		}

		// TODO 测试删除
		for (Entry<String, String> menu : filterChainDefinitionMap.entrySet()) {
			System.out.println(menu.getKey() + "------" + menu.getValue());
		}
	}

}
