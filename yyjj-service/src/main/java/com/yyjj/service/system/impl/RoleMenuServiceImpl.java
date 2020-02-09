package com.yyjj.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyjj.dao.system.RoleMenuMapper;
import com.yyjj.model.system.RoleMenu;
import com.yyjj.service.system.RoleMenuService;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
implements RoleMenuService
{

}
