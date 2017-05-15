package org.web.cape.web.index;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.web.cape.entity.MgrFunction;
import org.web.cape.entity.MgrUser;
import org.web.cape.repository.MgrFunctionJdbcDao;
import org.web.httpsession.cache.SessionCacheManager;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@Autowired
	MgrFunctionJdbcDao funDao;
    @Autowired
    private SessionCacheManager sessionCacheManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		String cuser = null;
		if (SecurityUtils.getSubject().getPrincipal() != null)
			cuser = (String) SecurityUtils.getSubject().getPrincipal();
		MgrUser uservo = sessionCacheManager.getCurUser(cuser);
		List<MgrFunction> funlist = funDao.findAllFuncsByUserId(uservo.getId());
		
		model.addAttribute("funList", funlist);
		
		model.addAttribute("cusername", cuser==null?"":cuser);

		return "index";
	}
}
