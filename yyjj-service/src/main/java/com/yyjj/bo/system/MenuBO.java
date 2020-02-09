package com.yyjj.bo.system;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.yyjj.model.system.Menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuBO extends Menu implements Serializable{
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
     * 子菜单
     */
    private List<MenuBO> subMenus;
    
    public static MenuBO newInstance(Menu menu) {
        if(Objects.isNull(menu)) {
  	    return null;
  	  }        
        MenuBO menuBO = new MenuBO();
        BeanUtils.copyProperties(menu, menuBO);
        return menuBO;
  	}
    
   
  	    
  	public Menu convert() {
  		Menu menu = new Menu();
  	  BeanUtils.copyProperties(this, menu);
  	  return menu;
  	}
}
