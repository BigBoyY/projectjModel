package com.yyjj;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

public class ConvertUtil {
	
	public static <R, T> List<R> converter(List<T> list, Function<T, R > function) {
		if(CollectionUtils.isEmpty(list)) {
			return null;
		};
		List<R> records = list.stream().map(function).collect(Collectors.toList());		
		return records;
	}
	
	public <R, T> List<R> converterAll(Function<List<T>, List<R>> function,List<T> list) {
		return function.apply(list);
	}
}
