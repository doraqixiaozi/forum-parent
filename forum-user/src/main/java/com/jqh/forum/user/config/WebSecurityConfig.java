package com.jqh.forum.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    //authorizeRequests()表示开始配置授权
    //权限认证分两部分，一部分为拦截的路径，另一部分为所需的权限
    //.antMatchers("/**").permitAll()表示所有权限都可以访问所有路径
    //.anyRequest().authenticated()表示所有请求都需认证后访问
    //.and().csrf().disable();表示csrf拦截失效
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
