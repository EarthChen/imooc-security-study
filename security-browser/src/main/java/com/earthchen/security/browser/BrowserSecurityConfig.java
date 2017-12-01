package com.earthchen.security.browser;

import com.earthchen.security.browser.authentication.ImoocAuthenctiationFailureHandler;
import com.earthchen.security.browser.authentication.ImoocAuthenticationSuccessHandler;
import com.earthchen.security.core.properties.SecurityProperties;
import com.earthchen.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证错误处理器
     */
    @Autowired
    private ImoocAuthenctiationFailureHandler imoocAuthenctiationFailureHandler;

    /**
     * 验证成功处理器
     */
    @Autowired
    private ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 设置加密解密算法
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 系统在启动时会自动生成表
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenctiationFailureHandler);
        // 把配置传入验证码过滤器
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        http.
                addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 指定表单登录
                .formLogin()
                    // 配置登录页面
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/authentication/form")
                    // 配置登录成功处理器
                    .successHandler(imoocAuthenticationSuccessHandler)
                    // 配置失败处理器
                    .failureHandler(imoocAuthenctiationFailureHandler)

                .and()
                // 配置记住我功能
                .rememberMe()
                    // 配置记住我token
                    .tokenRepository(persistentTokenRepository())
                    // 配置记住我的秒数
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)

                .and()
                .authorizeRequests()
                    .antMatchers("/authentication/require",
                            securityProperties.getBrowser().getLoginPage(),
                            "/code/image").permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf().disable()
        ;
    }
}
