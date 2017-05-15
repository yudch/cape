package org.web.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web.httpsession.cache.SessionCacheManager;
import org.web.utils.CookieUtil;

public class StatelessLogoutFilter extends LogoutFilter {
	private static final Logger log = LoggerFactory.getLogger(StatelessLogoutFilter.class);
    
	@Autowired
	private SessionCacheManager cacheManager;
	
	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            subject.logout();

            doLogout((HttpServletRequest) request, (HttpServletResponse) response);
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        issueRedirect(request, response, redirectUrl);
        return false;
    }

    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
    	cacheManager.disCacheUser(CookieUtil.findCookieValue(request.getCookies(), Constants.PARAM_USERNAME));
    	
        response.addCookie(CookieUtil.expireCookieWithPath(Constants.PARAM_TOKEN, Constants.COOKIES_PATH));
        response.addCookie(CookieUtil.expireCookieWithPath(Constants.PARAM_USERNAME,Constants.COOKIES_PATH));
    }

    public void setSessionCacheManager(SessionCacheManager sessionCacheManager) {
        this.cacheManager = sessionCacheManager;
    }
}
