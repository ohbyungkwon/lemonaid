//package com.demo.lemonaid.demo.Config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${resource.id:spring-boot-application}")
//    private String resourceId;
//
//
//// TODO: resource 서버가 분리되어있다면 주석을 해제. accessTokenConverter 에는 keypair 생성시 발급한 Public-key를 주입받는다.
//
////    @Value("${security.oauth2.resource.jwt.key-value}")
////    private String publicKey;
////
////    @Bean
////    public TokenStore tokenStore() {
////        return new JwtTokenStore(accessTokenConverter());
////    }
////
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        //converter.setSigningKey("secret");
////        converter.setVerifierKey(publicKey);
////        return converter;
////    }
////
////    @Bean
////    @Primary
////    public DefaultTokenServices tokenService() {
////        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
////        defaultTokenServices.setTokenStore(tokenStore());
////        defaultTokenServices.setSupportRefreshToken(true);
////        return defaultTokenServices;
////    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/api/**").hasAnyRole("USER", "ADMIN", "TOCKER");
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(resourceId);
//    }
//
//}
