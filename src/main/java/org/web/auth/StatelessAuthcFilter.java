package org.web.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.web.utils.CookieUtil;

public class StatelessAuthcFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        
    	boolean isAjax = isAjax(request);
    	
    	//1、客户端发送来的摘要
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	String tokenStr = CookieUtil.findCookieValue(httpRequest.getCookies(), Constants.PARAM_TOKEN);
    	String ajaxUserName = CookieUtil.findCookieValue(httpRequest.getCookies(), Constants.PARAM_USERNAME);
    	
        //2、客户端传入的用户身份
        String username = request.getParameter(Constants.PARAM_USERNAME);
        if(username == null){
        	username = ajaxUserName;
        }
        
        boolean needCheck = !include((HttpServletRequest)request);
        
        if(needCheck){
        	if(tokenStr==null || username==null){
        		if(isAjax){
        			onAjaxAuthFail(request, response);
        		} else {
        			onLoginFail(request, response);
        		}
        		return false;
        	}
        	
        	//3、客户端请求的参数列表
        	Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        	params.remove(Constants.PARAM_DIGEST);
        	
        	//4、生成无状态Token
        	StatelessToken token = new StatelessToken(username, params, new String(tokenStr));
        	
        	try {
        		//5、委托给Realm进行登录
        		getSubject(request, response).login(token);
        	} catch (Exception e) {
        		e.printStackTrace();
        		if(isAjax && e instanceof AuthenticationException){
        			onAjaxAuthFail(request, response); //6、验证失败，返回ajax调用方信息
        			return false;
        		} else {
        			onLoginFail(request, response); //6、登录失败，跳转到登录页
        			return false;
        		}
        	}
        	return true;
        } else {
        	return true;
        }
        
    }

	private boolean isAjax(ServletRequest request) {
		boolean isAjax = false;
        if(request instanceof HttpServletRequest){
        	HttpServletRequest rq = (HttpServletRequest)request;
        	String requestType = rq.getHeader("X-Requested-With");
			if (requestType != null && "XMLHttpRequest".equals(requestType)) {
				isAjax = true;
			}
        }
		return isAjax;
	}
    
    private void onAjaxAuthFail(ServletRequest request, ServletResponse resp) throws IOException{
    	HttpServletResponse response = (HttpServletResponse) resp;
    	JSONObject json = new JSONObject();
		json.put("msg", "auth check error!");
		response.setStatus(Constants.HTTP_STATUS_AUTH);
		response.getWriter().write(json.toString());
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(Constants.HTTP_STATUS_AUTH);
        // httpResponse.getWriter().write("login error, auth uncorrect."); 
        // 跳转到登录页
        redirectToLogin(request, httpResponse);
    }
    
    public boolean include(HttpServletRequest request) {
		String u = request.getRequestURI();
		for (String e : Constants.esc) {
			if (u.endsWith(e)) {
				return true;
			}
		}
		return false;
	}
}