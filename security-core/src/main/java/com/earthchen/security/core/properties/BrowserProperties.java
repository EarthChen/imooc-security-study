package com.earthchen.security.core.properties;


public class BrowserProperties {

    /**
     * 登录页
     */
    private String loginPage = "/signIn.html";

    /**
     * 登录响应类型
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * 记住我时间秒数
     */
    private int rememberMeSeconds = 3600;


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
