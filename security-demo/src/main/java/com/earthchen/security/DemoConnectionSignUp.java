package com.earthchen.security;


import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;


/**
 * 配置ConnectionSignUp实现类
 *
 * 实现用户使用第三方登录完默认注册一个用户并登录
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    /* (non-Javadoc)
     * @see org.springframework.social.connect.ConnectionSignUp#execute(org.springframework.social.connect.Connection)
     */
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }

}