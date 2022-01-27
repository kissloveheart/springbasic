package com.example.springboot.config;

import com.example.springboot.filter.LoginFilter;
import com.example.springboot.filter.RegisInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterBean(){
        FilterRegistrationBean<LoginFilter> loginFilterRegistrationBean =
                new FilterRegistrationBean<>();
        loginFilterRegistrationBean.setFilter( new LoginFilter());
        loginFilterRegistrationBean.addUrlPatterns("/login");
        return loginFilterRegistrationBean;
    }

}
