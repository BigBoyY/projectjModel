package com.yyjj.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.dao.system.UserMarkMapper;
import com.yyjj.model.system.UserMark;
import com.yyjj.service.system.UserMarkService;

@Service
public class UserMarkServiceImpl extends ServiceImpl<UserMarkMapper, UserMark>
implements UserMarkService
{

}
