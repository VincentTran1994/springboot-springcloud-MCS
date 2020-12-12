package com.example.demo.seurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment environment;

    @Autowired
    public WebSecurity(Environment environment){
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers(environment.getProperty("api.h2console.url.path")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path")).permitAll()
//                .antMatchers(environment.getProperty("api.port.url.path")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(),environment));

        // This will help to make all the request will be stateless and all of the request will not be creating
        // session for whatever request has made. So that, we don't the call will be always required JWT token
        // attached in its header
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
