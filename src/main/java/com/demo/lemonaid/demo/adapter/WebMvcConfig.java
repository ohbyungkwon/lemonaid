package com.demo.lemonaid.demo.adapter;

import com.demo.lemonaid.demo.interceptor.OrderInterceptor;
import com.demo.lemonaid.demo.interceptor.WrongUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private OrderInterceptor orderInterceptor;
    private WrongUserInterceptor wrongUserInterceptor;

    @Autowired
    WebMvcConfig(OrderInterceptor orderInterceptor, WrongUserInterceptor wrongUserInterceptor){
        this.orderInterceptor = orderInterceptor;
        this.wrongUserInterceptor = wrongUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderInterceptor)
                .addPathPatterns("/order");
//                .excludePathPatterns("/**");

        registry.addInterceptor(wrongUserInterceptor)
                .addPathPatterns("/WrongUser");
    }
}
