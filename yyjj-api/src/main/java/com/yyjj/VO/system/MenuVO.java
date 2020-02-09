package com.yyjj.VO.system;

import java.awt.Menu;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.yyjj.bo.system.MenuBO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuVO implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Integer id;
	/**
     * 菜单编号
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口路径
     */
    private String url;

    /**
     * 是否启用
     */
    private Integer enable;
    
    /**
     * 是否选中
     */
    private Boolean checked;
    
    /**
     * 子菜单
     */
    private List<MenuVO> subMenus; 
    public static MenuVO newInstance(Menu menu) {
        if(Objects.isNull(menu)) {
  	    return null;
  	  }        
        MenuVO menuVO = new MenuVO();
        menuVO.setChecked(false);
        BeanUtils.copyProperties(menu, menuVO);
        return menuVO;
  	}
    
    public static MenuVO newInstanceByBO(MenuBO menu) {
        if(Objects.isNull(menu)) {
  	    return null;
  	  }        
        MenuVO menuVO = new MenuVO();
        menuVO.setChecked(false);
        BeanUtils.copyProperties(menu, menuVO);
        return menuVO;
  	}
   
  	    
  	public Menu convert() {
  		Menu menu = new Menu();
  	  BeanUtils.copyProperties(this, menu);
  	  return menu;
  	}
}
