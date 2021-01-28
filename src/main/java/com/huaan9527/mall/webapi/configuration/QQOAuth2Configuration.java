package com.huaan9527.mall.webapi.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.qq.ProviderType;
import org.springframework.social.qq.configuration.QqProperties;
import org.springframework.social.qq.oauth2.QqConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;


@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(QqProperties.class)
public class QQOAuth2Configuration implements SocialConfigurer {

    private final QqProperties properties;
    private DataSource dataSource;
    private ConnectionSignUp connectionSignUp;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        ProviderType providerType = ProviderType.valueOf(properties.getProviderType());
        final QqConnectionFactory factory = new QqConnectionFactory(providerType, properties.getAppId(),
                properties.getAppSecret(), properties.getAuthorizeUrl(),
                properties.getAccessTokenApiUrl());
        factory.setScope(properties.getScope());
        connectionFactoryConfigurer.addConnectionFactory(factory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        connectionRepository.setConnectionSignUp(connectionSignUp);
        return connectionRepository;
    }

}
