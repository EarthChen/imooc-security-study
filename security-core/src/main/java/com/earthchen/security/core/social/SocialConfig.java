package com.earthchen.security.core.social;

import com.earthchen.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


/**
 *
 */
@EnableSocial
@Configuration
public class SocialConfig extends SocialConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return new JdbcUsersConnectionRepository(dataSource,
////                connectionFactoryLocator,
////                Encryptors.noOpText());
        JdbcUsersConnectionRepository repository =
                new JdbcUsersConnectionRepository(dataSource,
                        connectionFactoryLocator,
                        Encryptors.noOpText());
        // 设置UserConnection表的前缀
        repository.setTablePrefix("security_");
        return repository;
    }

    @Bean
    public SpringSocialConfigurer earthchenSecurityConfig(){
//        return new SpringSocialConfigurer();
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
        return configurer;
    }
}
