package com.yyjj.controller.system;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import com.yyjj.ConvertUtil;
import com.yyjj.VO.system.RoleVO;
import com.yyjj.VO.system.UserAddVO;
import com.yyjj.VO.system.UserDetail;
import com.yyjj.VO.system.UserUpdateVO;
import com.yyjj.VO.system.UserVO;
import com.yyjj.bo.system.UserBO;
import com.yyjj.constant.dynamic.ApiLogConstant;
import com.yyjj.controller.dynamic.ActivityEum;
import com.yyjj.controller.dynamic.ChangeFieldHolder;
import com.yyjj.controller.dynamic.annotation.ApiId;
import com.yyjj.controller.dynamic.annotation.ApiLog;
import com.yyjj.controller.dynamic.annotation.ApiUpdate;
import com.yyjj.exception.CodeRepeatErrorException;
import com.yyjj.exception.LoginErrorException;
import com.yyjj.model.dynamic.ChangeField;
import com.yyjj.model.system.Role;
import com.yyjj.model.system.User;
import com.yyjj.model.system.UserRole;
import com.yyjj.service.BasePage;
import com.yyjj.service.system.RoleService;
import com.yyjj.service.system.UserRoleService;
import com.yyjj.service.system.UserService;
import com.yyjj.shiro.MySessionManager;
import com.yyjj.util.MD5Util;

/**
 * 用户管理
 * @author yml
 *
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserRoleService UserRoleService;

	@Autowired
	private MySessionManager sessionManager;

	@Autowired
	RoleService roleService;

	
	/**
	 * 获取所有用户
	 * 
	 * @param userVO
	 * @return
	 */
	@GetMapping
	public BasePage<UserVO> list(UserVO userVO) {
		UserBO bo = new UserBO();
		bo.setUserName(userVO.getUserName());
		userVO.setUserName(null);
		bo.setUserId(userVO.getId());
		userVO.setId(null);
		return userService.listSearch(userVO.convert(), bo).converterAll(this::convert);
	}
	 

	/**
	 * 退出登录
	 */
	@GetMapping("/logout")
	public void logout() {

		SecurityUtils.getSubject().logout();
	}

	/**
	 * 用户登录
	 * 
	 * @param vo
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/login")
	public UserVO login(@RequestBody @Validated UserDetail vo) throws UnsupportedEncodingException {
		// 从SecurityUtils里边创建一个 subject
		Subject subject = SecurityUtils.getSubject();
		// 在认证提交前准备 token（令牌）
		UsernamePasswordToken token = new UsernamePasswordToken(vo.getUserAccount(), vo.getPassword());
		// 执行认证登陆
		try {
			subject.login(token);
		} catch (UnknownAccountException uae) {
			throw new LoginErrorException("未知账户");
		} catch (IncorrectCredentialsException ice) {
			throw new LoginErrorException("密码不正确");
		} catch (LockedAccountException lae) {
			throw new LoginErrorException("账户已停用");
		} catch (ExcessiveAttemptsException eae) {
			throw new LoginErrorException("用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			throw new LoginErrorException("用户名或密码不正确！");
		} catch (UnknownSessionException use) {
			throw new LoginErrorException("session失效");
		}
			Session newSession = SecurityUtils.getSubject().getSession();
		if (subject.isAuthenticated()) {
//			log.info("==============认证成功");
			for (Session session : sessionManager.getSessionDAO().getActiveSessions()) {				
//				log.info("先登录用户账号==============="+session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY).toString());
//				log.info("先登陆用户sessionid============"+session.getId());
//				log.info("后登陆用户账号==============="+vo.getUserAccount());
//				log.info("后登陆用户sessionid============"+newSession.getId());
				Object userSessionKey = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if (Objects.nonNull(userSessionKey) && vo.getUserAccount().equals(userSessionKey.toString())
						&& ! session.getId().equals(newSession.getId())
						) {				
//					log.info("==============用户被提出");
					session.setTimeout(1000);
				}
			}
			
			UserDetail object = (UserDetail) newSession.getAttribute("user");
			UserVO userVO = UserVO.newInstance((UserDetail) object);
			//userVO.setSessionId(session.getId());
			return userVO;
		} else {
			token.clear();
			SecurityUtils.getSubject().getSession().removeAttribute("user");
			throw new LoginErrorException("登陆失败");
		}
//        	
//		User user = userService.lambdaQuery()
//		.eq(User::getUserName, vo.getUserName()).one();
//		if(Objects.nonNull(user)) {
//			if(user.getPassword().equals(vo.getPassword())) {
//				request.getSession().setMaxInactiveInterval(7200);
//				request.getSession().setAttribute("user", user);
//				log.info(String.valueOf(request.getSession().getMaxInactiveInterval()));
//				return UserVO.newInstance(user);
//			}else
//			{
//				throw new LoginErrorException("密码错误");
//			}
//		}
//		return new UserVO();
	}

	/**
	 * 获取某种权限的所有用户
	 * 
	 * @param userVO
	 * @return
	 */
	@GetMapping("{roleId:\\d+}/role")
	public List<UserVO> listUserByRole(@PathVariable Integer roleId) {
		List<UserRole> userRoleList = UserRoleService.lambdaQuery().eq(UserRole::getRoleId, roleId).list();
		List<Integer> userIds = userRoleList.stream().map(UserRole::getUserId).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(userIds)) {
			// TODO 后期修改
			return userService.lambdaQuery().eq(User::getUserStatus, true).in(User::getId, userIds).list().stream().map(UserVO::newInstance)
					.collect(Collectors.toList());
		}
		return null;
	}

	/**
	 * 新增用户
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping
	@ApiLog(activity = ActivityEum.ADD,type = "用户",messageType = ApiLogConstant.IMPORTANCE)
	public UserVO add(
			//@ApiAdd("userName")
			@RequestBody @Validated UserAddVO vo) {
		User user = userService.lambdaQuery().eq(User::getUserAccount, vo.getUserAccount()).or()
				.eq(User::getUserName, vo.getUserName()).one();
		if (Objects.nonNull(user)) {
			throw new CodeRepeatErrorException("用户名重复");
		}
		user = vo.convert();
		if(Objects.isNull(user.getPassword())) {
			user.setPassword("123");
		}
		user.setPassword(MD5Util.getMD5String(user.getPassword()));
		userService.save(user);
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = null;
		for (RoleVO role : vo.getRoles()) {
			userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(role.convert().getId());
			userRoles.add(userRole);
		}
		UserRoleService.saveBatch(userRoles);
		return UserVO.newInstance(user);
	}

	/**
	 * 修改用户
	 * 
	 * @param vo
	 * @return
	 */
	@PutMapping
	@ApiLog(activity = ActivityEum.PUT,type = "用户",messageType = ApiLogConstant.IMPORTANCE)
	public UserVO modify(
			@ApiUpdate
			@ApiId(value = UserService.class,detailParam = "userName",idName="id")
			@RequestBody @Validated UserUpdateVO vo) {
		User user = userService.lambdaQuery().eq(User::getUserName, vo.getUserName()).ne(User::getId, vo.getId()).one();
		if (Objects.nonNull(user)) {
			throw new CodeRepeatErrorException("用户名重复");
		}
		ChangeField changeField = new ChangeField();
		changeField.setChangeBefor(userService.getById(vo.getId()));
		
		user = vo.convert();
		userService.updateById(user);	
		changeField.setChangeAfter(user);
		ChangeFieldHolder.setChangeField(changeField);
		// TODO 更改用户名后，为确保数据正确性， changeUserName(user);
		// 修改权限
		if (!CollectionUtils.isEmpty(vo.getRoles())) {
			UserRoleService.changeUserRole(user.getId(),
					vo.getRoles().stream().map(RoleVO::convert).collect(Collectors.toList()));
		}
		for (Session session : sessionManager.getSessionDAO().getActiveSessions()) {
			if (user.getUserAccount().equals(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY).toString())) {
				
				UserDetail userDetail =	(UserDetail) session.getAttribute("user");
				List<UserRole> userRoles = UserRoleService.lambdaQuery().eq(UserRole::getUserId, vo.getId()).list();
				if(! CollectionUtils.isEmpty(userRoles)) {
					userDetail.setRoles(
							ConvertUtil.converter(
							roleService.lambdaQuery().in(Role::getId, userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList())).list()
							,RoleVO::newInstance)
							);
					session.setAttribute("user", userDetail);
				}
				
			}
		}
		 //(UserDetail) SecurityUtils.getSubject().getSession().getAttribute("user");	
		return UserVO.newInstance(vo.convert());

	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	@DeleteMapping("/{id:\\d+}")
	@ApiLog(activity = ActivityEum.DELETE,type = "用户",messageType = ApiLogConstant.IMPORTANCE)
	public void remove(
			@ApiId(value = UserService.class,detailParam = "userName")
			@PathVariable Integer id) {
		User user = userService.getById(id);
		user.setEnable(false);
		user.setUserAccount(user.getId().toString());
		user.setUserName("账号已删除");
		user.setUserStatus(false);
		
		userService.updateById(user);
		UserRoleService.remove(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, id));
	}
	/**
	 * 修改密码
	 */
	@PutMapping("/changePassword")
	public UserVO modifyPassword(@RequestBody UserUpdateVO vo) {
		UserDetail user = (UserDetail) SecurityUtils.getSubject().getSession().getAttribute("user");
		User u = userService.getById(user.getId());
		if (user.getPassword().equals(MD5Util.getMD5String(vo.getPassword()))) {
			String newPassword = MD5Util.getMD5String(vo.getNewPassword());
			u.setPassword(newPassword);
			user.setPassword(newPassword);
			SecurityUtils.getSubject().getSession().setAttribute("user", user);
			userService.updateById(u);
		} else {
			throw new LoginErrorException("密码不正确");
		}
		u.setPassword(null);
		return UserVO.newInstance(u);
	}

//	/**
//	 * 
//	 * 
//	 * @param file
//	 * @return
//	 */
//	@PostMapping("/icon")
//	public UserVO ReplaceIcon(MultipartFile file) {
//		UserDetail user = (UserDetail) SecurityUtils.getSubject().getSession().getAttribute("user");
//		User u = userService.getById(user.getId());
//		// FileLibrary fileLibrary =
//		// fileService.upload(file,FileUploadConstant.STATEMENT,FileCategory.ICON);
//		// TODO
//		u.getUserIcon();
//		return null;
//	}

	/**
	 * 启用用户
	 * 
	 * @param id
	 */
	@PutMapping("{id:\\d+}/enable")
	@ApiLog(activity = ActivityEum.ENABLE,type = "用户",messageType = ApiLogConstant.IMPORTANCE)
	public void enable(
			@ApiId(value = UserService.class,detailParam = "userName")
			@PathVariable Integer id) {
		
		userService.update(new UpdateWrapper<User>().lambda().eq(User::getId, id).eq(User::getUserStatus, true).set(User::getEnable, true));
	}

	/**
	 * 停用用户
	 * 
	 * @param id
	 */
	@PutMapping("{id:\\d+}/disable")
	@ApiLog(activity = ActivityEum.DISABLE,type = "用户",messageType = ApiLogConstant.IMPORTANCE)
	public void disable(
			@ApiId(value = UserService.class,detailParam = "userName")
			@PathVariable Integer id) {
		userService.update(new UpdateWrapper<User>().lambda().eq(User::getId, id).set(User::getEnable, false));

	}

	private BasePage<UserVO> convert(BasePage<User> basePage) {
		List<UserVO> resultList = new ArrayList<UserVO>();

		for (User User : basePage.getRecords()) {
			if (!Objects.isNull(User)) {
				UserVO vo = UserVO.newInstance(User);
				List<UserRole> list = UserRoleService.lambdaQuery().eq(UserRole::getUserId, vo.getId()).list();
				List<RoleVO> roleList = new ArrayList<>();
				for (UserRole userRole : list) {
					Role role = roleService.getById(userRole.getRoleId());
					roleList.add(RoleVO.newInstance(role));
				}
				vo.setRoles(roleList);
				// 计算比例
			
				resultList.add(vo);
			}
		}
		BasePage<UserVO> result = new BasePage<UserVO>(basePage.getPage(), basePage.getPageSize(),
				basePage.getCurrent(), basePage.getTotal(), resultList);
		return result;
	}
}
