//package com.demo.lemonaid.demo.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//
//import javax.sql.DataSource;
//import javax.validation.Valid;
//import java.security.KeyPair;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//    @Value("${resouce.id:spring-boot-application}")
//    private String resourceId;
//
//    @Value("${access_token.validity_period:3600}")
//    int accessTokenValiditySeconds = 3600;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private DataSource dataSource;
//
//    public TokenStore tokenStore(){
//        return new JwtTokenStore(accessTokenConverter());
//    }
//    public JwtAccessTokenConverter accessTokenConverter(){
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("server.jks"), "passtwo".toCharArray()).getKeyPair("auth", "passone".toCharArray());
////        converter.setSigningKey("secret");
//        converter.setKeyPair(keyPair);
//        return converter;
//    }
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenService() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.accessTokenConverter(accessTokenConverter()).authenticationManager(this.authenticationManager);
//        //토큰을 등록하고 메니저를 등록 후
//    }
//
//    @Bean
//    @Primary
//    public JdbcClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        //oauth_client_details 테이블에 등록된 사용자로 조회한다.
//        clients.withClientDetails(clientDetailsService);
//    }
//}
