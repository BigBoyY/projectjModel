package com.yyjj.service;

import java.util.LinkedList;
import java.util.Objects;

public class BasePageVO {

    private Long page;
    
    private Long pageSize;
    
    private LinkedList<OrderItems> orders;//排序
    
    private Boolean asc ;
    public BasePageVO() { }
    
    public BasePageVO(Long page, Long pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }

	public Long getPage() {
        return Objects.isNull(page)? 1: page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

	public Boolean getAsc() {
		return asc;
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

	public LinkedList<OrderItems> getOrders() {
		return orders;
	}

	public void setOrders(LinkedList<OrderItems> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "BasePageVO [page=" + page + ", pageSize=" + pageSize + ", orders=" + orders + ", asc=" + asc + "]";
	}

	
   
    
}
