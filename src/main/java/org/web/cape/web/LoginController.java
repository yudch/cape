package org.web.cape.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
import org.web.auth.Constants;
import org.web.cape.entity.MgrUser;
import org.web.cape.service.account.AccountService;
import org.web.esapi.EncryptException;
import org.web.httpsession.cache.SessionCacheManager;
import org.web.utils.RSAUtils;
import org.web.utils.TokenGenerator;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final int HASH_INTERATIONS = 1024;
	// 默认一天
	public static final int COOKIES_MAXAGE = 60 * 60 * 24;
	
    @Autowired
    private SessionCacheManager sessionCacheManager;

	@Autowired
	protected AccountService accountService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		initPubKeyParams(model);
		return "login";
	}
	
	private void initPubKeyParams(Model model) {
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
    	String publicKeyExponent = publicKey.getPublicExponent().toString(16);//16进制
 	    String publicKeyModulus = publicKey.getModulus().toString(16);//16进制
 	    model.addAttribute("exponent", publicKeyExponent);
 	    model.addAttribute("modulus", publicKeyModulus);
	}
	
	@RequestMapping(method = RequestMethod.POST,value="formLogin")
	public String formLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String userName = request.getParameter("username");
        String encryptedPassWord = request.getParameter("password");
        encryptedPassWord = encryptedPassWord.replace("_encrypted", "");
        String passWord = RSAUtils.decryptStringByJs(encryptedPassWord);
		
		MgrUser user = accountService.findUserByLoginName(userName);
		if (passWord!=null && user != null) {
			byte[] hashPassword = Digests.sha1(passWord.getBytes(), Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
			String checkPwd = Encodes.encodeHex(hashPassword);
			if(checkPwd.equals(user.getPassword())){
                long ts = System.currentTimeMillis();

                user.setLoginTs(ts);
                String cookieValue = "";
                try {
                    cookieValue = TokenGenerator.genToken(userName, ts, sessionCacheManager.findSeed());
                } catch (EncryptException e) {
                    logger.error("Fail to generate cookie!", e);
                }

                // 校验成功，写cookie
                HashMap<String, String> cookiesMap = new HashMap<String, String>();
                cookiesMap.put(Constants.PARAM_USERNAME, userName);
                cookiesMap.put(Constants.PARAM_TOKEN, cookieValue);
                for (Iterator<String> iterator = cookiesMap.keySet().iterator(); iterator.hasNext(); ) {
                    String key = iterator.next();
                    Cookie cookie = new Cookie(key, URLEncoder.encode(cookiesMap.get(key)));
                    cookie.setPath(Constants.COOKIES_PATH);
                    //浏览器关闭时候删除cookie
                    cookie.setMaxAge(-1);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
                
                //把登陆信息写入到redis缓存中
                try {
                    sessionCacheManager.cacheUser(userName, user);
                } catch (Exception e) {
                    logger.error("登陆信息写入到redis缓存中失败!", e);
                }
            } else {
            	initPubKeyParams(model);
                model.addAttribute("accounterror", "用户名密码错误!");
                return "login";
            }
            return "redirect";
		} else {
			initPubKeyParams(model);
            model.addAttribute("accounterror", "你输入的用户不存在!");
            return "login";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}
