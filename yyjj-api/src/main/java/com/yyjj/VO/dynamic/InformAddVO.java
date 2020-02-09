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
public class InformAddVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
     
    public static  InformAddVO newInstance( Inform  inform) {
        if(Objects.isNull( inform)) {
  	    return null;
  	  }        
         InformAddVO  informAddVO = new  InformAddVO();
        BeanUtils.copyProperties( inform,  informAddVO);
        return  informAddVO;
  	}
    
    public static  InformAddVO newInstance( InformBO  inform) {
        if(Objects.isNull( inform)) {
  	    return null;
  	  }        
         InformAddVO  informAddVO = new  InformAddVO();
        BeanUtils.copyProperties( inform,  informAddVO);
        return  informAddVO;
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