package com.earthchen.security.core.properties;

/**
 * 短信验证码配置项
 */
public class SmsCodeProperties {

    /**
     * 短信验证码的长度
     */
    private int length = 6;

    /**
     * 过期时间
     */
    private int expireIn = 60;

    /**
     * 需要处理的url
     */
    private String url;


    public int getLength() {
        return length;
    }

    public void setLength(int lenght) {
        this.length = lenght;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
