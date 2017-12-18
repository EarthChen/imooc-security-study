package com.earthchen.security.browser;

import javax.sql.DataSource;

import com.earthchen.security.core.properties.SecurityConstants;
import com.earthchen.security.core.properties.SecurityProperties;
import com.earthchen.security.core.validate.code.ValidateCodeSecurityConfig;
import com.earthchen.security.core.validate.code.authentication.AbstractChannelSecurityConfig;
import com.earthchen.security.core.validate.code.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;


@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer earthchenSocialConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;





    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 系统在启动时会自动生成表
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;

    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//
//        //allow Swagger URL to be accessed without authentication
//        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
//                "/swagger-resources/configuration/ui",//用来获取支持的动作
//                "/swagger-resources",//用来获取api-docs的URI
//                "/swagger-resources/configuration/security",//安全选项
//                "/swagger-ui.html");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
//        // 把配置传入验证码过滤器
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();
//
//        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
//        smsCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
//        smsCodeFilter.setSecurityProperties(securityProperties);
//        smsCodeFilter.afterPropertiesSet();
//
//
//        http
//                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                // 指定表单登录
//                .formLogin()
//                // 配置登录页面
//                .loginPage("/authentication/require")
//                .loginProcessingUrl("/authentication/form")
//                // 配置登录成功处理器
//                .successHandler(imoocAuthenticationSuccessHandler)
//                // 配置失败处理器
//                .failureHandler(imoocAuthenticationFailureHandler)
//
//                .and()
//                // 配置记住我功能
//                .rememberMe()
//                // 配置记住我token
//                .tokenRepository(persistentTokenRepository())
//                // 配置记住我的秒数
//                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//                .userDetailsService(userDetailsService)
//
//                .and()
//                .authorizeRequests()
//                .antMatchers("/authentication/require",
//                        securityProperties.getBrowser().getLoginPage(),
//                        "/code/*",
//                        "/v2/api-docs",//swagger api json
//                        "/swagger-resources/configuration/ui",//用来获取支持的动作
//                        "/swagger-resources",//用来获取api-docs的URI
//                        "/swagger-resources/configuration/security",//安全选项
//                        "/swagger-ui.html"
//                ).permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf().disable()
//
//                // 将sms验证码配置项添加到浏览器配置中
//                .apply(smsCodeAuthenticationSecurityConfig)
//        ;

        applyPasswordAuthenticationConfig(http);

        http
                // 应用验证码安全配置
                .apply(validateCodeSecurityConfig)
                    .and()
                // 应用短信验证码认证安全配置
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                // 引用社交配置
                .apply(earthchenSocialConfig)
                    .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                // session 配置
                .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    // 设置session失效之后跳转到的url
    //                .invalidSessionUrl("/session/invalid")
    //                // 设置最大session数量
    //                .maximumSessions(1)
    //                //当session数量达到最大时，阻止后来的用户登录
    //                //.maxSessionsPreventsLogin(true)
    //                // session超时处理策略
    //                .expiredSessionStrategy(new ImoocExpiredSessionStrategy())
                    .and()
                    .and()
                .logout()
                    .logoutUrl("/signOut")
                    //.logoutSuccessUrl("/imooc-logout.html")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                    .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getRegisterPage(),
//                        securityProperties.getBrowser().getSignOutUrl(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        "/user/register",
                        "/session/invalid",
                        "/v2/api-docs",//swagger api json
                        "/swagger-resources/configuration/ui",//用来获取支持的动作
                        "/swagger-resources",//用来获取api-docs的URI
                        "/swagger-resources/configuration/security",//安全选项
                        "/swagger-ui.html").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();


    }
}
