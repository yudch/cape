package org.web.cape.web.demo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.Servlets;
import org.web.cape.entity.Demo;
import org.web.cape.service.demo.DemoService;

@Controller
@RequestMapping(value = "/example/demo")
public class DemoController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DemoService demoService;

	@Autowired
	private Validator validator;

	@RequestMapping(value = "page", method = RequestMethod.GET)
	public @ResponseBody Page<Demo> page(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
	    searchParams = Servlets.getParametersStartingWith(request, "search_");

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);

		Page<Demo> categoryPage = demoService.getDemoPage(searchParams, pageRequest);
		return categoryPage;
	}

	/** 进入新增 */
	@RequestMapping(value="create", method=RequestMethod.GET)
	public @ResponseBody Demo add() {
		Demo entity = new Demo();
		return entity;
	}

	/** 保存新增 */
    @RequestMapping(value="create", method=RequestMethod.POST)
    public @ResponseBody Object create(@RequestBody Demo entity, HttpServletRequest resq) {
    	JSONObject json = new JSONObject();
    	BeanValidators.validateWithException(validator, entity);
    	try {
    		entity = demoService.saveEntityByJDBC(entity);
    		json.put("msg", "新增成功!");
    		json.put("flag", "success");
		} catch (Exception e) {
			//记录日志
			logger.error("保存出错!",e);
			json.put("flag", "fail");
			json.put("msg", "更新失败!!");
		}
        return json;
    }

	/**
	 * 进入更新界面
	 *
	 * @param id
	 * @param model
	 * @return 需要更新的实体的json结构
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public @ResponseBody Demo updateForm(@PathVariable("id") String id, Model model) {
		Demo entity = demoService.getDemoById(id);
		return entity;
	}

	/** 保存更新 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody Object update(@RequestBody Demo entity) {
		JSONObject json = new JSONObject();
    	try {
    		entity = demoService.saveEntityByJDBC(entity);
    		json.put("flag", "success");
    		json.put("msg", "更新成功!");
		} catch (Exception e) {
			logger.error("更新出错!",e);
			json.put("flag", "fail");
			json.put("msg", "更新失败!!");
		}
		return json;
	}

	/**
	 * 删除实体
	 *
	 * @param id 删除的标识
	 * @return 是否删除成功
	 */
	@RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable("id") String id) {
		demoService.deleteDemoByIdUseSql(id);
		return true;
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}



	@RequestMapping(value = "deleteBatch", method = RequestMethod.GET)
	public @ResponseBody String delBatch(HttpServletRequest resq) {
		try{
			demoService.delBatchJDBC(resq);
			//demoService.delBatchJPA(resq); //jpa方式批量删除
			return "error" ;
		}catch(Exception e){
			logger.error("刪除异常",e);
		}
		return "success" ;
	}
}
