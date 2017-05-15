package org.web.cape.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springside.modules.beanvalidator.BeanValidators;

public abstract class BaseController {
	
	/**
	 * 创建分页请求.
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
 
    public void renderToHtml(ConstraintViolationException ce,HttpServletRequest resq,HttpServletResponse response){
    	try {
    		Map<String,String> errMap = BeanValidators.extractPropertyAndMessage(ce);
			StringBuffer errsb = new StringBuffer("");
			 for (String key : errMap.keySet()) {  
				 	errsb.append(errMap.get(key));
				    errsb.append(",");
			   }  
			response.getWriter().write(errsb.toString().substring(0, errsb.toString().length()-1));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
