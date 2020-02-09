package com.yyjj.VO.dynamic;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.yyjj.bo.dynamic.InformBO;
import com.yyjj.model.dynamic.Inform;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author yml
 * 
 */
@Getter
@Setter
public class InformUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
     
    public static  InformUpdateVO newInstance( Inform  inform) {
        if(Objects.isNull( inform)) {
  	    return null;
  	  }        
         InformUpdateVO  informUpdateVO = new  InformUpdateVO();
        BeanUtils.copyProperties( inform,  informUpdateVO);
        return  informUpdateVO;
  	}
    
    public static  InformUpdateVO newInstance( InformBO  inform) {
        if(Objects.isNull( inform)) {
  	    return null;
  	  }        
         InformUpdateVO  informUpdateVO = new  InformUpdateVO();
        BeanUtils.copyProperties( inform,  informUpdateVO);
        return  informUpdateVO;
  	}
  	    
  	public  Inform convert() {
  		 Inform  inform = new  Inform();
  	  BeanUtils.copyProperties(this,  inform);
  	  return  inform;
  	}
  	
  	public  InformBO  InformBO() {
  		 InformBO  inform = new  InformBO();
  	  BeanUtils.copyProperties(this,  inform);
  	  return  inform;
  	}
  	
  }