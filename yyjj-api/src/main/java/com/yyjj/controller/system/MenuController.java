package com.yyjj.controller.system;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yyjj.VO.system.MenuVO;
import com.yyjj.bo.system.MenuBO;
import com.yyjj.model.system.RoleMenu;
import com.yyjj.service.system.MenuService;
import com.yyjj.service.system.RoleMenuService;

/**
 * 菜单管理
 * @author yml
 *
 */
@RestController
@RequestMapping("menu")
@SuppressWarnings("rawtypes")
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleMenuService roleMenuService;
	
//	@Autowired
//	private SessionDAO sessionDAO;
	
	/**
	 * 获取所有菜单
	 * @return
	 */
	@GetMapping
	public List<MenuVO> list(){
		return menuService.getMenuList().stream().map(MenuVO::newInstanceByBO).collect(Collectors.toList());
	}
	
	/**
	 * 获取指定角色的菜单
	 */
	@GetMapping("/{roleId:\\d+}")
	public List<MenuVO> listByRole(@PathVariable Integer roleId) {
		List<MenuVO> list = menuService.getMenuList().stream().map(MenuVO::newInstanceByBO).collect(Collectors.toList());
		initMenusTreeByRid(list,roleMenuService.lambdaQuery().eq(RoleMenu::getRoleId, roleId).list());
//		for (Session session : sessionDAO.getActiveSessions()) {
//     	  System.out.println(session.getAttributeKeys());
//        } 
		return list;

	}
	/**
	 * 遍历菜单并设置好checked属性
	 * @param menus
	 * @param rms
	 * @param column 
	 */	
	@SuppressWarnings("unchecked")
	private  void initMenusTreeByRid(List menus,List<RoleMenu> rms) {
		for (int i = 0; i < menus.size(); i++) {
			//只要是叶子节点才需要设置checked=true
			MenuVO Menu = null;
			if(menus.get(i) instanceof MenuVO) {
				 Menu=(MenuVO) menus.get(i);
			}else  {
				Menu = MenuVO.newInstanceByBO((MenuBO)menus.get(i));
			}
			
			if(! CollectionUtils.isEmpty(Menu.getSubMenus())) {
				initMenusTreeByRid(Menu.getSubMenus(),rms);
			}
			if(checkNode(Menu,menus)) {	
				//在权限表中是否有该节点	
				//遍历权限角色表判断该角色是否有该菜单
				for (RoleMenu sysRole : rms) {	
					if(sysRole.getMenuId() == Menu.getId()) {						
						Menu.setChecked(true);								
					}
				}
			}
			menus.set(i, Menu);
		}
	}
	
	/**
	 * 判断指定菜单节点是否是叶子节点
	 * @param menu
	 * @param menus
	 * @return
	 */
	private  boolean checkNode(MenuVO menu,List menus) {
		 boolean flag = true;//true代表叶子节点
		 //判断
		 for (int i = 0; i < menus.size(); i++) {
			 MenuVO Menu = null;
				if(menus.get(i) instanceof MenuVO) {
					 Menu=(MenuVO) menus.get(i);
				}else  {
					Menu = MenuVO.newInstanceByBO((MenuBO)menus.get(i));
				}
				if(Menu.getParentId() == menu.getId()||menu.getParentId()==0) {
					//menu不是叶子节点
				/*	System.out.println("不是叶子节点");*/
					flag = false;
					break;
				}				
		}
		 return flag;
			
		
		
	}
}
