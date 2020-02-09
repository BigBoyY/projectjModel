package com.yyjj.controller.system;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yyjj.VO.system.MenuVO;
import com.yyjj.VO.system.RoleVO;
import com.yyjj.bo.system.RoleBO;
import com.yyjj.constant.dynamic.ApiLogConstant;
import com.yyjj.controller.dynamic.ActivityEum;
import com.yyjj.controller.dynamic.ChangeFieldHolder;
import com.yyjj.controller.dynamic.annotation.ApiId;
import com.yyjj.controller.dynamic.annotation.ApiLog;
import com.yyjj.controller.dynamic.annotation.ApiUpdate;
import com.yyjj.model.dynamic.ChangeField;
import com.yyjj.model.system.Role;
import com.yyjj.model.system.RoleMenu;
import com.yyjj.service.BasePage;
import com.yyjj.service.system.MenuService;
import com.yyjj.service.system.RoleMenuService;
import com.yyjj.service.system.RoleService;

/**
 * 角色管理
 * @author yml
 *
 */
@RestController
@RequestMapping("role")
public class RoleController {
		
	@Autowired
	RoleService roleService;
	
	@Autowired
	RoleMenuService roleMenuService;
	
	@Autowired
	ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Autowired
	MenuService menuService;

	//private static final Logger log = LoggerFactory.getLogger(ProjectVoteController.class);
	/**
	 * 所有权限
	 * @param vo
	 * @return
	 */
	@GetMapping
	public BasePage<RoleVO> list(RoleVO vo) throws UnknownSessionException {
//		 for (Session session : sessionManager.getSessionDAO().getActiveSessions()) {
//         	sessionManager.getSessionDAO().delete(session);
//         }
		//for (Session session : sessionManager.getSessionDAO().getActiveSessions()) {
		//	log.info(session.getId().toString());
		//}
		RoleBO bo = new RoleBO();
		bo.setRoleCode(vo.getRoleCode());
		vo.setRoleCode(null);
		bo.setRoleName(vo.getRoleName());
		vo.setRoleName(null);
		return roleService.listSearch(vo.convert(), bo).converter(RoleVO::newInstance);
	}
	
	/**
	 * 新增权限
	 * @param vo
	 * @return
	 */
	@PostMapping
	@ApiLog(activity = ActivityEum.ADD, type = "权限",messageType = ApiLogConstant.IMPORTANCE)
	public RoleVO add(
			//@ApiAdd("roleName")
			@RequestBody @Validated RoleVO vo) {
		//TODO  后期增加菜单权限关联
		//vo.getMenus()
		Role role = vo.convert();
		roleService.save(role);
		List<RoleMenu> roleMenus = new ArrayList<>();
		RoleMenu roleMenu = null;
		for (MenuVO menu : vo.getMenus()) {
			roleMenu = new RoleMenu(); 
			roleMenu.setMenuId(menu.getId());
			roleMenu.setRoleId(role.getId());
			roleMenus.add(roleMenu);
		}
		roleMenuService.saveBatch(roleMenus);
		//重新加载权限
		updatePermission();
		return vo;
	}
	
	/**
	 * 修改权限
	 * @param vo
	 * @return
	 */
	@PutMapping("/{id:\\d+}")
	@ApiLog(activity = ActivityEum.PUT,type = "权限",messageType = ApiLogConstant.IMPORTANCE)
	public RoleVO modify(
			@ApiId(value =RoleService.class,detailParam = "roleName") 
			@PathVariable Integer id,
			@ApiUpdate
			@RequestBody @Validated RoleVO vo) {
		vo.setId(id);
		List<MenuVO> menus = vo.getMenus();
		ChangeField changeField = new ChangeField();		
		changeField.setChangeBefor(roleService.getById(id));
		Role role = vo.convert();
		roleService.updateById(role);
		changeField.setChangeAfter(role);
		ChangeFieldHolder.setChangeField(changeField);
		roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, vo.getId()));
				
		List<RoleMenu> mList = new ArrayList<RoleMenu>();
		RoleMenu roleMenu = null;
		for (MenuVO menuVO : menus) {
			if(menuVO.getChecked() && menuVO.getParentId() != 0) {
				roleMenu = new RoleMenu();
				roleMenu.setMenuId(menuVO.getId());
				roleMenu.setRoleId(vo.getId());
				mList.add(roleMenu);
			}
		}		
		roleMenuService.saveBatch(mList);
			
		//重新加载权限
		updatePermission();
		return vo;
	}
	
	 /**
     * 重新加载权限
     */
    public void updatePermission() {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver)    shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            //清空session
            //for (Session session : sessionManager.getSessionDAO().getActiveSessions()) {
            //	sessionManager.getSessionDAO().delete(session);
          //  }	 
        }
    }
    /**
     * 重新加载权限
     * @return
     */
	private Map<String, String> loadFilterChainDefinitions() {
		 Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		 	
	        // 配置不会被拦截的链接 顺序判断
		 	filterChainDefinitionMap.put("/webSocket", "anon");  
	    	filterChainDefinitionMap.put("/doc/**", "anon");  
	        filterChainDefinitionMap.put("/user/**==GET", "anon");      
	        filterChainDefinitionMap.put("/static/**", "anon");
	        filterChainDefinitionMap.put("/unauthorized", "anon");
	        filterChainDefinitionMap.put("/unauth", "anon");
	        filterChainDefinitionMap.put("/user/login", "anon");
	        filterChainDefinitionMap.put("/file/**", "anon");
// 			List<ShiroMenuUrl> shiroMenuUrlList = loginService.selectShiroMenuUrl();
//	        for(ShiroMenuUrl shiroMenuUrl : shiroMenuUrlList){
//	            filterChainDefinitionMap.put(shiroMenuUrl.getUrl(), "authc,perms[" + shiroMenuUrl.getShiroUrl() +"]");
//	        }
	        menuService.initAuthorization(filterChainDefinitionMap);
	        filterChainDefinitionMap.put("/**", "authc");
	        return filterChainDefinitionMap;
	}
	/**
	 * 权限详情
	 * @return
	 */
	@GetMapping("/{id:\\d+}")
	public RoleVO getById(@PathVariable Integer id) {
		RoleVO vo = RoleVO.newInstance(roleService.getById(id));
		//List<Integer> menuIds = roleMenuService.lambdaQuery().eq(RoleMenu::getRoleId, id)
		//.list().stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
		return vo;
	}
	
	
	/**
	 * 启用角色
	 * @param id
	 */
	@PutMapping("{id:\\d+}/enable")
	@ApiLog(activity = ActivityEum.ENABLE,type = "权限",messageType = ApiLogConstant.IMPORTANCE)
	public void enable(
			@ApiId(value = RoleService.class,detailParam = "roleName")
			@PathVariable Integer id) {
		roleService.update(new UpdateWrapper<Role>().lambda()
				.eq(Role::getId, id)
				.set(Role::getEnable, true)
				);
		updatePermission();
	}
	
	
	/**
	 * 停用角色
	 * @param id
	 */
	@PutMapping("{id:\\d+}/disable")
	@ApiLog(activity = ActivityEum.DISABLE,type = "权限",messageType = ApiLogConstant.IMPORTANCE)
	public void disable(
			@ApiId(value = RoleService.class,detailParam = "roleName")
			@PathVariable Integer id) {
		roleService.update(new UpdateWrapper<Role>().lambda()
				.eq(Role::getId, id)
				.set(Role::getEnable, false)
				);
		updatePermission();
	}
	
	
	/**
	 * 删除权限
	 * @param id
	 */
	@DeleteMapping("{id:\\d+}")
	@ApiLog(activity = ActivityEum.DELETE,type = "权限",messageType = ApiLogConstant.IMPORTANCE)
	public void remove(
			@ApiId(value = RoleService.class,detailParam = "roleName")
			@PathVariable Integer id) {
		roleService.removeById(id);
		roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, id));
		updatePermission();
	}
}