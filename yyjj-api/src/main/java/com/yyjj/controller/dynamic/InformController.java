package com.yyjj.controller.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyjj.VO.dynamic.InformAddVO;
import com.yyjj.VO.dynamic.InformUpdateVO;
import com.yyjj.VO.dynamic.InformVO;
import com.yyjj.constant.dynamic.ApiLogConstant;
import com.yyjj.model.dynamic.Inform;
import com.yyjj.service.BasePage;
import com.yyjj.service.dynamic.InformService;


/**
 * Inform
 * @author yml
 *
 */
@RestController
@RequestMapping("/Inform")
public class InformController {
		
	@Autowired
	InformService informService;
	
	/**
	 * 动态消息列表
	 * @param vo
	 * @return
	 */
	@GetMapping
	public BasePage<InformVO> listAll(InformVO vo){
		return informService.listPage(new QueryWrapper<Inform>().lambda().ne(Inform::getMessageType, ApiLogConstant.PRO_INDIFFERENT)
				.ne(Inform::getMessageType, ApiLogConstant.AFTER_INDIFFERENT)
				.orderByDesc(Inform::getCreateTime)).converter(InformVO::newInstance);
	}
	
	
	/**
	 * 重要消息列表
	 * @param vo
	 * @return
	 */
	@GetMapping("/importance")
	public BasePage<InformVO> listImportance(InformVO vo){
		return informService.listPage(new QueryWrapper<Inform>().lambda().eq(Inform::getMessageType, ApiLogConstant.IMPORTANCE).orderByDesc(Inform::getCreateTime)).converter(InformVO::newInstance);

	}
	
	/**
	 *
	 * @param id Informid
	 * @return
	 */
	@GetMapping("/{id:\\d+}")
	public InformVO Detail(@PathVariable Integer id) {
		
		return null;
	}
	
	
	/**
	 * æ°å¢Inform
	 * @param vo
	 * @return
	 * 
	 */
	@PostMapping
	public InformVO add(@RequestBody @Validated InformAddVO vo) {
		return null;	
	}
	
	/**
	 * ä¿®æ¹Inform
	 * @param vo
	 * @return
	 * 
	 */
	@PutMapping
	public InformVO modify(@RequestBody @Validated InformUpdateVO vo) {
		return null;	
	}
	
	/**
	 * å é¤Inform
	 * @param id
	 */
	@DeleteMapping("/{id:\\d+}/delete")
	public void remove(@PathVariable Integer id) {
		
	}
	
	private BasePage<InformVO> convert(BasePage<Inform> basePage) {
		List<InformVO> resultList = new ArrayList<InformVO>();
					
		for (Inform inform : basePage.getRecords()) {
			
		}
		BasePage<InformVO> result = new BasePage<InformVO>(basePage.getPage(),
				basePage.getPageSize(), basePage.getCurrent(), basePage.getTotal(), resultList);
		return result;
	} 
	
	
}
