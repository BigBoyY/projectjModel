package com.yyjj.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yyjj.VO.system.RoleVO;
import com.yyjj.VO.system.UserDetail;
import com.yyjj.model.system.Role;
import com.yyjj.model.system.User;
import com.yyjj.model.system.UserRole;
import com.yyjj.service.system.RoleService;
import com.yyjj.service.system.UserRoleService;
import com.yyjj.service.system.UserService;

public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;
	
//	@Autowired
//
//	private SessionDAO sessionDAO;
	private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		UserDetail user = (UserDetail) SecurityUtils.getSubject().getSession().getAttribute("user");
		//权限
		//HashSet<Integer> menuIds = new HashSet<>();
		for (RoleVO role : user.getRoles()) {	
			//角色
			//System.out.println(111);
			authorizationInfo.addStringPermission(role.getRoleName()); 
        }
//		for (String strings : authorizationInfo.getStringPermissions()) {
//			System.out.println(strings);
//		}
		return authorizationInfo;

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (token.getPrincipal() == null) {
			return null;
		}
		String userName = (String) token.getPrincipal();
		User user = userService.lambdaQuery().eq(User::getUserAccount, userName).one();
		// String userPwd = new String((char[]) token.getCredentials());
		// UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		// 2、判断用户名称是否存在
		
		if (Objects.isNull(user)) {
			return null;
		} else {
			// 3、判断密码是否正确
			if (!user.getEnable()) {
				throw new LockedAccountException();
			}
			UserDetail userDetail = UserDetail.newInstance(user);
			List<UserRole> userRoles = userRoleService.lambdaQuery().eq(UserRole::getUserId, user.getId()).list();
			List<RoleVO> roleVOs = new ArrayList<RoleVO>();
			//List<MenuVO> menuVOs = null;
			//用户角色
			for (UserRole userRole : userRoles) {
				
				Role role = roleService.getById(userRole.getRoleId());
				//是否启用
				if (role.getEnable()) {
					RoleVO roleVO = RoleVO.newInstance(role);
//					List<RoleMenu> roleMenus = roleMenuService.lambdaQuery().eq(RoleMenu::getRoleId, role.getId())
//							.list();
//					//TODO 后期删除
//					menuVOs = new ArrayList<MenuVO>();
//					//角色菜单
//					for (RoleMenu roleMenu : roleMenus) {
//						Menu menu = menuService.getById(roleMenu.getMenuId());						
//						menuVOs.add(MenuVO.newInstance(menu));
//					}
//					roleVO.setMenus(menuVOs);
					log.info(roleVO.getRoleName());
					roleVOs.add(roleVO);
				}
			}
			userDetail.setRoles(roleVOs);
		
//			String loginName=token.getUsername();
//
//			Session currentSession = null;
//
//			Collection<Session> sessions = sessionDAO.getActiveSessions();
//
//			for(Session session:sessions){
//
//			if(loginName.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY))) {
//
//			session.setTimeout(0);//设置session立即失效，即将其踢出系统
//
//			break;
			
			SecurityUtils.getSubject().getSession().setAttribute("user", userDetail);
			return new SimpleAuthenticationInfo(userName, user.getPassword(), getName());
		}

	}

}
