package com.earthchen.security.app.server;

import com.earthchen.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.earthchen.security.core.authorize.AuthorizeConfigManager;
import com.earthchen.security.core.properties.SecurityConstants;
import com.earthchen.security.core.properties.SecurityProperties;
import com.earthchen.security.core.validate.code.ValidateCodeSecurityConfig;
import com.earthchen.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer earthchenSocialConfig;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler);

        http
                 //应用验证码安全配置
                .apply(validateCodeSecurityConfig)
                .and()
                 //应用短信验证码认证安全配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                // 引用社交配置
                .apply(earthchenSocialConfig)
                .and()

                .apply(openIdAuthenticationSecurityConfig)
                .and()

                .csrf().disable();

        // 引用默认配置
        authorizeConfigManager.config(http.authorizeRequests());
    }
}