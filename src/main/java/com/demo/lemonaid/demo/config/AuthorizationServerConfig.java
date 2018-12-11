package com.demo.lemonaid.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        converter.setSigningKey("secret");
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter());
    }

    private static final String APPNAME = "testapp";
    private static final String APPKEY = "$2a$10$EHqL/a.q50N2tTtSbMt3d.wAeJ7WvZwC4fomp9qKoFTiJu.zYwRZm";

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO: oauth_client_details 테이블로 앱 시크릿 관리하고 싶은경우 사용
        clients.inMemory()
                .withClient(APPNAME).secret(APPKEY)
                .authorizedGrantTypes("password", "authorization_code", "implicit", "refresh_token")
                .authorities("ROLE_YOUR_CLIENT")
                .scopes("read", "write")
                .resourceIds("obk-resource")
                .accessTokenValiditySeconds(36000);
    }
}
