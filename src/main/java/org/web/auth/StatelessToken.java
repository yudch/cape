package org.web.auth;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;

public class StatelessToken implements AuthenticationToken {

	private static final long serialVersionUID = -7316737568510866049L;
	
	private String username;
	// 预留参数集合，校验更复杂的权限
    private Map<String, ?> params;
    private String clientDigest;

    public StatelessToken(String username,  Map<String, ?> params, String clientDigest) {
        this.username = username;
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public  Map<String, ?> getParams() {
        return params;
    }

    public void setParams( Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
       return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }
}