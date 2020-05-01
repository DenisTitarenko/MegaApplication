package com.titarenko.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${admin-role.spring.security}")
    private String admin;
    @Value("${admin-role.name.spring.security}")
    private String admin_name;
    @Value("${admin-role.password.spring.security}")
    private String admin_pass;

    @Value("${user-role.spring.security}")
    private String user;
    @Value("${user-role.name.spring.security}")
    private String user_name;
    @Value("${user-role.password.spring.security}")
    private String user_pass;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/grouped", "/samesalary", "/project/", "/department/").hasAnyRole(user, admin)
                .antMatchers("/**").hasRole(admin)
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/", true)
                .and().logout().logoutSuccessUrl("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(user_name).password(user_pass).roles(user)
                .and()
                .withUser(admin_name).password(admin_pass).roles(admin);
    }
}
