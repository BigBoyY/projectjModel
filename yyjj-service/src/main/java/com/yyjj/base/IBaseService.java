package com.yyjj.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyjj.bo.base.IBaseBO;
import com.yyjj.context.BasePageVOContextHolder;
import com.yyjj.service.BasePage;
import com.yyjj.service.BasePageVO;
import com.yyjj.service.OrderItems;


public interface IBaseService<T> extends IService<T> {

	default BasePage<T> listPage(T t) {
		BasePageVO basePageVO = BasePageVOContextHolder.getBasePageVO();
		return list(t, basePageVO);
	}

	default BasePage<T> list(T t, BasePageVO basePageVO) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
		return list(queryWrapper, basePageVO);
	}

	default BasePage<T> listPage(Wrapper<T> queryWrapper) {
		BasePageVO basePageVO = BasePageVOContextHolder.getBasePageVO();
		return list(queryWrapper, basePageVO);
	}

	default BasePage<T> listSearch(T t, IBaseBO<T> baseBO) {
		BasePageVO basePageVO = BasePageVOContextHolder.getBasePageVO();
		return listSearch(t, baseBO, basePageVO);
	}

	default BasePage<T> listSearch(T t, IBaseBO<T> baseBO, BasePageVO basePageVO) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
		if (null != queryWrapper && null != baseBO) {
			baseBO.accpet(queryWrapper);
		}
		return list(queryWrapper, basePageVO);
	}

	default BasePage<T> list(Wrapper<T> queryWrapper, BasePageVO basePageVO) {
		BasePage<T> basePage = new BasePage<T>();

		if (Objects.nonNull(basePageVO) && Objects.nonNull(basePageVO.getPageSize())) {
			Page<T> page = new Page<>(basePageVO.getPage(), basePageVO.getPageSize());
			
			setOrders(page,basePageVO.getOrders());
			IPage<T> iPage = this.page(page, queryWrapper);
			
			basePage.setPage(basePageVO.getPage());
			basePage.setPageSize(basePageVO.getPageSize());
			basePage.setCurrent(iPage.getCurrent());
			basePage.setTotal(iPage.getTotal());
			basePage.setRecords(iPage.getRecords());
			
		} else {			
			if (Objects.nonNull(basePageVO) &&! CollectionUtils.isEmpty(basePageVO.getOrders())) {
				Page<T> page = new Page<>();
				setOrders(page,basePageVO.getOrders());
				IPage<T> iPage = this.page(page, queryWrapper);			
				basePage.setPage(basePageVO.getPage());
				basePage.setPageSize(basePageVO.getPageSize());
				basePage.setCurrent(iPage.getCurrent());
				basePage.setTotal(iPage.getTotal());
				basePage.setRecords(iPage.getRecords());
			}else {
				List<T> list = this.list(queryWrapper);
				Long total = Long.valueOf(list.size());
				basePage.setTotal(total);
				basePage.setPageSize(total);
				basePage.setRecords(list);
				basePage.setCurrent(basePage.getPage());
			}
		}

		return basePage;
	}
	
	default void setOrders(Page<T> page,List<OrderItems> list) {
		if( ! CollectionUtils.isEmpty(list)) {
			List<OrderItem> orderItemList = new ArrayList<OrderItem>();
			for (OrderItems orderItem : list) {
				OrderItem oItem = new OrderItem();
				oItem.setAsc(orderItem.isAsc());
				oItem.setColumn(orderItem.getColumn());
				orderItemList.add(oItem);
			}
			page.setOrders(orderItemList);				
		}
	}
	
}
