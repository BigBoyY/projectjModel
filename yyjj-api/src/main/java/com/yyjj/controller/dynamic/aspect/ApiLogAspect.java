package com.yyjj.controller.dynamic.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yyjj.VO.system.UserDetail;
import com.yyjj.context.SpringApplicationUtils;
import com.yyjj.controller.dynamic.ApiLogContextHolder;
import com.yyjj.controller.dynamic.ChangeFieldHolder;
import com.yyjj.controller.dynamic.annotation.After;
import com.yyjj.controller.dynamic.annotation.ApiId;
import com.yyjj.controller.dynamic.annotation.ApiLog;
import com.yyjj.controller.dynamic.annotation.ApiUpdate;
import com.yyjj.controller.dynamic.annotation.Pro;
import com.yyjj.handel.ChatMessageHandler;
import com.yyjj.handel.DBHandler;
import com.yyjj.model.dynamic.ChangeField;
import com.yyjj.model.dynamic.Inform;
import com.yyjj.service.dynamic.InformService;

/**
 * 拦截获取档案日志注解的信息
 * 
 * @author yml
 *
 */
@Aspect
@Component
public class ApiLogAspect {
	
	@Autowired
	private InformService informService;

	@Autowired
	private DBHandler DBHandler;
	
	
	// 定义切点 --> 拦截MaintainLog
	@Pointcut("@annotation(com.yyjj.controller.dynamic.annotation.ApiLog)")
	public void ApiLog() {
	}
	private static final Logger log = LoggerFactory.getLogger(ApiLogAspect.class);

	/**
	 * 执行前获取控制器参数信息
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Before("ApiLog()")
	public void doBefore(JoinPoint pjp) throws Throwable {
		Inform inform = null;
		// 获取控制器
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取具体的方法
		Method method = methodSignature.getMethod();
		// 获取注解
		ApiLog apiLog = method.getAnnotation(ApiLog.class);
		
		if (Objects.nonNull(apiLog)) {
			//当前用户
			UserDetail user = (UserDetail) SecurityUtils.getSubject().getSession().getAttribute("user");		
				inform = new Inform();
				inform.setActivity(apiLog.activity().toString());
				inform.setUserId(user.getId());
				inform.setUserIcon(user.getUserIcon());
				inform.setUserName(user.getUserName());
				inform.setCreateTime(LocalDateTime.now());
				inform.setType(apiLog.type());
				inform.setMessageType(apiLog.messageType());
				ApiLogContextHolder.setInform(inform);
		}
		
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				/*if (annotation instanceof ApiAdd) {
					ApiAdd apiAdd = (ApiAdd) annotation;
					Object detail = doGetAddDetail(pjp.getArgs()[i],apiAdd);
					if (detail instanceof String) {
						inform.setMethod(HttpMethod.POST.toString());
						inform.setDetail((String)detail);
					}
				}else*/ if(annotation instanceof ApiId) {
					ApiId apiDelete = (ApiId) annotation;
					Object detail = doGetIdDetail(pjp.getArgs()[i],apiDelete);
					if (detail instanceof String) {
						inform.setDetail((String)detail);
						inform.setMethod(HttpMethod.OPTIONS.toString());
					}
				}else if(annotation instanceof ApiUpdate) {
					//ApiUpdate apiUpdate = (ApiUpdate) annotation;					
					//Object detail = doGetUpdateDetail(pjp.getArgs()[i],apiUpdate);
					//if (detail instanceof String) {
					//	inform.setDetail((String)detail);
						inform.setMethod(HttpMethod.PUT.toString());
					//}
				}
				ApiLogContextHolder.setInform(inform);								
			}
		}
	}
	
	/**
	 * 解析注解中的service 执行ById查找到bean 并获取详细信息
	 * @param obj 参数
	 * @param update 注解
	 * @return
	 */
	private Object doGetUpdateDetail(Object obj,ApiUpdate update) {
		Field field;
		try {
			field = obj.getClass().getDeclaredField(update.value());
			if (Objects.nonNull(field)) {
				field.setAccessible(true);
				return field.get(obj);
			}
		} catch (Exception e) {
			return "";
		} 
		return "";	
	}
	
	/**
	 * 解析注解中的service 执行ById查找到bean 并获取详细信息
	 * @param obj 参数
	 * @param add 注解
	 * @return
	 */
	/*private Object doGetAddDetail(Object obj,ApiAdd add) {
		Field field;
		try {
			field = obj.getClass().getDeclaredField(add.value());
			if (Objects.nonNull(field)) {
				field.setAccessible(true);
				return field.get(obj);
			}
		} catch (Exception e) {
			return "";
		} 
		return "";	
	}*/
	
	private Object doGetIdDetail(Object obj,ApiId apiId) {
		
		IService<?> iService = SpringApplicationUtils.getBean(apiId.value());		
			try {
				if(! StringUtils.isEmpty(apiId.idName())) {
					Field field = obj.getClass().getDeclaredField(apiId.idName());
					field.setAccessible(true);
					 Object id;			
					id = field.get(obj);
					 if (obj instanceof Integer) {
						 Object detail = iService.getById((Integer)id);
						 return getDetail(detail,apiId);
					 }
				}
			}catch (Exception e) {			
				return "";
			}
		
		if (Objects.nonNull(obj)) {
			if (obj instanceof Integer) {
				Object detail = iService.getById((int) obj);
				return getDetail(detail,apiId);
			} else if (obj instanceof Integer[]) {
				Integer[] ids = (Integer[]) obj;				
				Collection<?> objList = iService.listByIds(Arrays.asList(ids));
				StringBuilder detailString = new StringBuilder();
				for (Object object : objList) {
					detailString.append(getDetail(object,apiId));
				}
				return detailString.toString();				
			}
		}
		return "";
	}
	
	private Object getDetail(Object entity, ApiId delete) {
		java.lang.reflect.Field field;
		try {
			field = entity.getClass().getDeclaredField(delete.detailParam());
			field.setAccessible(true);
			Object detail;
				detail = field.get(entity);			
			return  detail;
		} catch (Exception e) {
			return "";
		} 
		
	}
	
	
	/**
	 * 执行后获取控制器返回信息
	 * 方法返回说明接口调用成功，插入动态
	 * 
	 * @param pjp joinPoint
	 * @param rvt return的对象
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws Exception
	 */
	@AfterReturning(value = "ApiLog()", returning = "rvt")
	public void doAfterReturning(JoinPoint pjp, Object rvt) throws NoSuchFieldException, SecurityException {
		if(Objects.isNull(rvt)) {
			ApiLogContextHolder.removeInform();
			ChangeFieldHolder.removeChangeField();
			return ;
		}
		// 获取控制器
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取具体的方法
		Method method = methodSignature.getMethod();
	

		// 获取注解
		Inform inform = ApiLogContextHolder.getInform();
		StringBuilder builder = new StringBuilder();		
		//例：杨梦玲  新增了  用户  罗玉凤
		//    刘洋     推进了  业务阶段   由获取项目信息推进为初步尽职调查
		//    蒋伟     修改了 项目负责人 由XXX修改为YYY
		builder.append(inform.getUserName());//谁谁谁
		builder.append(inform.getActivity());//动作
		builder.append(inform.getType());//目标
		builder.append(inform.getDetail());//附加信息
		inform.setMessage(builder.toString());
		ChangeField changeField = ChangeFieldHolder.getChangeField();
		//修改数据的差异处理		
		informService.save(inform);
		boolean sendSokect = true;
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				if(annotation instanceof Pro) {					  
					 
					  
				}else if(annotation instanceof After) {
					 
				}
				if(annotation instanceof ApiUpdate) {
					informService.removeById(inform.getId());
					sendSokect = false;
					searchCondition(changeField.getChangeBefor(),changeField.getChangeAfter(),inform, pjp);	
				}
			}
		}
		if(sendSokect) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			mapper.registerModule(new JavaTimeModule());
			String json=null;
			try {
				json = mapper.writeValueAsString(inform);
			} catch (JsonProcessingException e) {
				json = inform.toString();
				e.printStackTrace();
			}
			ChatMessageHandler.sendMessageToUsers(new TextMessage(json));
		}
		ChangeFieldHolder.removeChangeField();
		ApiLogContextHolder.removeInform();		
	}
		
	// 根据不同的状态执行不同的日志注入方法
	// 使用集合封装档案
//		if (status.compareTo(MaintainLogEums.ADD) == 0) {
//			status.setChangeId(maintainLog, rvt);
//			maintainLogs.add(maintainLog);
//		} else if (status.compareTo(MaintainLogEums.PUT) == 0) {
//			Object object = maintainLog.getChangeBeferEntity();
//			// 进行差异化处理并注入maintainLog
//			searchCondition(object, rvt, maintainLog);
//			//logger.info(maintainLog.getChangeItems());
//			maintainLogs.add(maintainLog);
//		} else if (status.compareTo(MaintainLogEums.STOPS) == 0) {
//			batchCreateMaintain(ids, maintainLog, maintainLogs);
//		} else if (status.compareTo(MaintainLogEums.DELETES) == 0) {
//			batchCreateMaintain(ids, maintainLog, maintainLogs);
//		} else if (status.compareTo(MaintainLogEums.STARTS) == 0) {
//			batchCreateMaintain(ids, maintainLog, maintainLogs);
//		} else {
//			status.setChangeId(maintainLog, params);
//			maintainLogs.add(maintainLog);
//		}
//
//		// 为档案注入属性
//		for (MaintainLogInfo log : maintainLogs) {
//			IService<?> service = SpringApplicationUtils.getBean(maintainLog.getService());
//			Object object = service.getById(log.getChangeId());
//			// 根据对象中的外键找到附属类型对象并填充数据
//			maintainLog.getMaintainType().findTypeInfo(object, log);
//		}
//		// 保存日志
//		saveMaintainLog(maintainLogs);
//		// logger.info(maintainLog.toString());
//
//	}

	/**
	 * 为每一个id创建一个MaintainLogInfo
	 * 
	 * @param ids
	 * @param maintainLog
	 * @throws Exception
	 */
//	private List<MaintainLogInfo> batchCreateMaintain(Integer[] ids, MaintainLogInfo maintainLog,
//			List<MaintainLogInfo> maintainLogs) throws Exception {
//		if (Objects.isNull(ids)) {
//			return maintainLogs;
//		}
//		for (int i = 0; i < ids.length; i++) {
//			Integer id = ids[i];
//			MaintainLogInfo Log = new MaintainLogInfo();
//			BeanUtils.copyProperties(maintainLog, Log);
//			Log.setChangeId(id);
//
//			maintainLogs.add(Log);
//		}
//		return maintainLogs;

	/**
	 * 将档案插入数据库
	 */
//	private void saveMaintainLog(List<MaintainLogInfo> maintainLogs) {
//		for (MaintainLogInfo maintainLogInfo : maintainLogs) {
//			maintainLogService.save(maintainLogInfo.convert());
//		}
//
//	}

	/**
	 * 差异化处理
	 * 
	 * @param befer       修改前
	 * @param after       修改后
	 * @param maintainLog
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings("unchecked")
	private void searchCondition(Object befer, Object after, Inform inform, JoinPoint pjp)
			throws NoSuchFieldException, SecurityException {
		Integer proId = null;
		Integer afterId = null;
		// 获取控制器
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取具体的方法
		Method method = methodSignature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		//固定的日志信息前缀
		String message = inform.getMessage();
		//检索是否需要该日志是否需要同步到项目动态或投后动态
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation annotation : parameterAnnotations[i]) {
				
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> beferMap = mapper.convertValue(befer, Map.class);
		 log.info("修改前：" + beferMap.toString());

		Map<String, Object> afterMap = mapper.convertValue(after, Map.class);
		 log.info("修改后:" + afterMap.toString());
		// 遍历修改前字段
		
		for (Map.Entry<String, Object> entry : beferMap.entrySet()) {
			inform.setId(null);
			String key = entry.getKey();	
			if(DBHandler.getExcludeSet().contains(key)) {
				continue;
			}
			Object beforObj = entry.getValue();
			Object afterObj = afterMap.get(key);
			//大驼峰转小驼峰
			String name = befer.getClass().getSimpleName();
			name = name.substring(0,1).toLowerCase()+name.substring(1);
			String field = DBHandler.getTableMap(name).get(key);
			// 修改后的值
			// 如果修改前为空，修改后不为空
			if (Objects.isNull(beforObj) && Objects.nonNull(afterObj)) {
				// 添加一个差异
				inform.setDetail(field + " 变更由 " + beforObj + " 修改为 " + afterObj);				
				inform.setMessage(message+": "+inform.getDetail());
				addCondition(field, inform, proId, afterId);
				continue;
			}
			// 修改前不为空，
			if (Objects.nonNull(beforObj)) {
				// 修改后为空
				if (Objects.isNull(afterObj)) {
					inform.setDetail(field + " 变更由 " + beforObj + " 修改为 " + afterObj);
					inform.setMessage(message+": "+inform.getDetail());
					addCondition(field, inform, proId, afterId);
					// 添加一个差异
					continue;
				}
				// 修改后不为空并且值不相等
				log.info(afterObj.getClass().getSimpleName());
				//数据类型为BigDecimal
				if(afterObj instanceof BigDecimal) {					
					if (((BigDecimal) afterObj).compareTo((BigDecimal) beforObj) != 0) {						
						inform.setDetail(field + " 变更由 " + beforObj + " 修改为 " + afterObj);
						inform.setMessage(message+": "+inform.getDetail());					
						addCondition(field, inform, proId, afterId);
						// 添加一个差异
					}
				}else if (! afterObj.equals(beforObj)) {
					inform.setDetail(field + " 变更由 " + beforObj + " 修改为 " + afterObj);
					inform.setMessage(message+": "+inform.getDetail());					
					addCondition(field, inform, proId, afterId);
					// 添加一个差异
				}
			}
		}
	}
	
	/**
	 * 保存日志并同步到项目与投后
	 * @param field
	 * @param inform
	 * @param proId
	 * @param afterId
	 */
	public void addCondition(String field,Inform inform,Integer proId,Integer afterId) {		
		informService.save(inform);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.registerModule(new JavaTimeModule());
		String json=null;
		try {
			json = mapper.writeValueAsString(inform);
		} catch (JsonProcessingException e) {
			json = inform.toString();
			e.printStackTrace();
		}
		//推送消息
		ChatMessageHandler.sendMessageToUsers(new TextMessage(json));
		
		
	}
	
	/**
	 * 驼峰转下划线
	 */
	 private static Pattern humpPattern = Pattern.compile("[A-Z]");
	 public static String humpToLine(String str) {
	      Matcher matcher = humpPattern.matcher(str);
	     StringBuffer sb = new StringBuffer();
	     while (matcher.find()) {
	          matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
	      }
	       matcher.appendTail(sb);
	     return sb.toString();
	 } 
}