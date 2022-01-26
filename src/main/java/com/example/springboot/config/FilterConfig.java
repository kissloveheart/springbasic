package com.example.springboot.config;

import com.example.springboot.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
