package org.web.security.digest;

/**
 * Created by zengxs on 2015/9/6.
 */
public class RestHMacParam {

    private String requestPath;

    private String beginTs;

    private String expireTs;

    private String ipAuth;

    private String ipAddress;

    public RestHMacParam() {
    }

    public RestHMacParam(String requestPath, String beginTs, String expireTs, String ipAuth, String ipAddress) {
        this.requestPath = requestPath;
        this.beginTs = beginTs;
        this.expireTs = expireTs;
        this.ipAuth = ipAuth;
        this.ipAddress = ipAddress;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getBeginTs() {
        return beginTs;
    }

    public void setBeginTs(String beginTs) {
        this.beginTs = beginTs;
    }

    public String getExpireTs() {
        return expireTs;
    }

    public void setExpireTs(String expireTs) {
        this.expireTs = expireTs;
    }

    public String getIpAuth() {
        return ipAuth;
    }

    public void setIpAuth(String ipAuth) {
        this.ipAuth = ipAuth;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "RestHMacParam{" +
                "requestPath='" + requestPath + '\'' +
                ", beginTs='" + beginTs + '\'' +
                ", expireTs='" + expireTs + '\'' +
                ", ipAuth='" + ipAuth + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
