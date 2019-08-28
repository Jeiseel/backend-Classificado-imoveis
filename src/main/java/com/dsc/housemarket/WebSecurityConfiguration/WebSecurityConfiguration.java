package com.dsc.housemarket.WebSecurityConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/user").permitAll()
		.antMatchers(HttpMethod.POST,"/user").permitAll()
		.antMatchers(HttpMethod.GET,"/user").permitAll()
		.antMatchers(HttpMethod.DELETE,"/user/*").permitAll()
		.antMatchers(HttpMethod.POST,"/property").permitAll()
		.antMatchers(HttpMethod.GET,"/property").permitAll()
		.antMatchers(HttpMethod.DELETE,"/property").permitAll()
		.anyRequest().authenticated();
	}
}
