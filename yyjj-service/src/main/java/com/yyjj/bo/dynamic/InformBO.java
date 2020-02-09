package com.yyjj.bo.dynamic;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyjj.bo.base.IBaseBO;
import com.yyjj.model.dynamic.Inform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformBO implements IBaseBO<Inform>{
	
	@Override
	public void accpet(QueryWrapper<Inform> queryWrapper) {
		
	}
	
	public  Inform convert() {
  		 Inform  inform = new  Inform();
  	  BeanUtils.copyProperties(this,  inform);
  	  return  inform;
  	}
}
    