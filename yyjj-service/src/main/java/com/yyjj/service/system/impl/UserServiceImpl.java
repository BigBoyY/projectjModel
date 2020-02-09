package com.yyjj.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.dao.system.UserMapper;
import com.yyjj.model.system.User;
import com.yyjj.service.system.UserService;

/**
 * 用户
 * @author admin
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
