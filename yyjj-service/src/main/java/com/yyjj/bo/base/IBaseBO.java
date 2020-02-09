package com.yyjj.bo.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface IBaseBO<T> {

	void accpet(QueryWrapper<T> queryWrapper);

}
