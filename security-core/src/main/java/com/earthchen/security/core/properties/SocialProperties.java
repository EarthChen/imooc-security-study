package com.earthchen.security.core.properties;

/**
 * 社交登录相关配置项
 */
public class SocialProperties {

    /**
     * qq配置项
     *
     * @return
     */
    private QQProperties qq = new QQProperties();

    /**
     * 拦截社交登录过滤url
     */
    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
