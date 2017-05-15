package org.web.cape.service.account;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web.auth.StatelessToken;
import org.web.cape.entity.MgrUser;
import org.web.cape.service.account.ShiroDbRealm.ShiroUser;
import org.web.esapi.EncryptException;
import org.web.httpsession.cache.SessionCacheManager;
import org.web.utils.TokenGenerator;

public class StatelessRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(StatelessRealm.class);
	
	@Autowired
	protected AccountService accountService;
	
    @Autowired
    private SessionCacheManager sessionCacheManager;
	
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		MgrUser user = accountService.findUserByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return info;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String uname = (String) token.getPrincipal();
        String tokenPass = genTokenPass(uname);
        if (tokenPass == null) {
            logger.error("User [{}] not exists in System", uname);
            throw new AuthenticationException("User " + uname + " not exists in System");
        }
        return new SimpleAuthenticationInfo(uname, tokenPass, getName());
    }

    /**
     * 查找用户
     *
     * @param uname
     * @return
     */
    private String genTokenPass(String uname) {

        try {
            String seed = sessionCacheManager.findSeed();
            MgrUser user = sessionCacheManager.getCurUser(uname);
            if (user != null && user.getLoginTs() != 0) {
                return TokenGenerator.genToken(uname, user.getLoginTs(), seed);
            }
        } catch (EncryptException e) {
            logger.error("Fail to calculate Token Seed!", e);
        }
        return null;
    }
}