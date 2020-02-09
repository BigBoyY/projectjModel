package com.yyjj.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasePage<T> extends BasePageVO {

	private Long current;

	private Long total;

	private List<T> records;

	public BasePage() {
	}

	public BasePage(BasePageVO basePageVO) {
		this(basePageVO.getPage(), basePageVO.getPageSize(), null);
	}

	public BasePage(Long page, Long pageSize, List<T> records) {
		this(page, pageSize, 0L, 0L, records);
	}
	
	public BasePage(Long total, List<T> records) {
		super();
		this.total = total;
		this.records = records;
	}

	public BasePage(Long page, Long pageSize, Long current, Long total, List<T> records) {
		super(page, pageSize);
		this.current = current;
		this.total = total;
		this.records = records;
	}
	
	public BasePage(Long page, Long pageSize,Long total, List<T> records) {
		super(page, pageSize);
		this.total = total;
		this.records = records;
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> list) {
		this.records = list;
	}

	public <R> BasePage<R> converter(Function<T, R> function) {
		List<R> records = this.records.stream().map(function).collect(Collectors.toList());
		BasePage<R> basePage = new BasePage<>(this.getPage(), this.getPageSize(), this.getCurrent(), this.getTotal(),
				records);
		return basePage;
	}

	public <R> BasePage<R> converterAll(Function<BasePage<T>, BasePage<R>> function) {
		return function.apply(this);
	}

}
