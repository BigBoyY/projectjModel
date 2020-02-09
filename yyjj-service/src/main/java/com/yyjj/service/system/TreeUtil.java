package com.yyjj.service.system;

import java.util.ArrayList;
import java.util.List;

import com.yyjj.bo.system.MenuBO;

public class TreeUtil {
	
	
	/**
	 * 判断指定菜单节点是否是叶子节点
	 * @param menu
	 * @param menus
	 * @return
	 */
	public static boolean checkNode(MenuBO menu,List<MenuBO> menus) {
		 boolean flag = true;//true代表叶子节点
		 //判断
		 for (MenuBO sysMenu : menus) {
			if(sysMenu.getParentId() == menu.getId()||menu.getParentId()==0) {
				//menu不是叶子节点
			/*	System.out.println("不是叶子节点");*/
				flag = false;
				break;
			}
		}
		 return flag;
	}
	
		/**
		 * 查询一级节点的处理方法
		 * 参数：所有节点(select * from menu)
		 */		
		public static List<MenuBO> getParentMenu(List<MenuBO> list){
			List<MenuBO> parents = new ArrayList<>();
			for (MenuBO SysMenus : list) {
				if(SysMenus.getParentId() == 0) {
					//说明是一级节点
					//查找所有子节点
					SysMenus.setSubMenus(getChild((int)SysMenus.getId().longValue(),list));
					//加入到集合
					parents.add(SysMenus);
				}
			}
			return parents;
		}
		
		/**
		 * 根据指定id查找 其下所有子节点
		 * 参数：id
		 *参数：查询集合
		 * @param id
		 * @param list
		 * @return
		 */		
		public static List<MenuBO> getChild(int id,List<MenuBO> list){
			
			List<MenuBO> childs = new ArrayList<>();
			for (MenuBO SysMenus : list) {
				if(SysMenus.getParentId() == 0) {
					//如果是一级节点 跳过
					continue;
				}
				if(id == SysMenus.getParentId()) {					
					//递归查找子节点
					SysMenus.setSubMenus(getChild(SysMenus.getId(), list));					
					//加入返回的集合
					childs.add(SysMenus);
				}
			}
			return childs;
		}
}
