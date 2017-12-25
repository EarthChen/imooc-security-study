package com.earthchen.security;

import com.earthchen.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;


@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /**
     * demo项目授权配置
     * @param config
     * @return
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //config.antMatchers("/demo.html").hasRole("ADMIN");
        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
        return true;

    }
}
