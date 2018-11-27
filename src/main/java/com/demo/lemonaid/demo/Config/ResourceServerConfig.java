package com.demo.lemonaid.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@Order(1)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests().anyRequest().permitAll();

        http.antMatcher("/jwt/saveMapLocation")
                .authorizeRequests()
                .anyRequest().hasAnyAuthority("ROLE_ADMIN", "ROLE_MASTER_USER", "ROLE_USER")
                .and()
                .antMatcher("/jwt/**").authorizeRequests().anyRequest().permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("obk-resource");
    }

}
