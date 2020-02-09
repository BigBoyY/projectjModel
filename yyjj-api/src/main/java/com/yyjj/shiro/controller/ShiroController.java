package com.yyjj.shiro.controller;

import org.apache.shiro.session.UnknownSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyjj.shiro.Result;

@Controller
public class ShiroController {
	
	 /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息，由前端控制跳转页面
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/unauth")
    public Result unauth() {
		throw new UnknownSessionException("用户未登录");
	}
	
	/**
     * 权限不足，shiro应重定向到403界面，此处返回权限不足信息，由前端控制跳转页面
     */
    @ResponseBody
    @RequestMapping(value = "/unauthorized")
    public Result unauthorized(){
    	return new Result(403,"权限不足");
    }
}
