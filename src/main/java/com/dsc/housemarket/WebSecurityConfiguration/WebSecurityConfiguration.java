package com.dsc.housemarket.WebSecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
		http.headers().frameOptions().disable();
		/*
		http.csrf().disable().authorizeRequests()
		.antMatchers("/user").permitAll()
		.antMatchers(HttpMethod.POST,"/user").permitAll()
		.antMatchers(HttpMethod.GET,"/user").permitAll()
		.antMatchers(HttpMethod.DELETE,"/user/*").permitAll()
		.antMatchers(HttpMethod.POST,"/property").permitAll()
		.antMatchers(HttpMethod.GET,"/property").permitAll()
		.antMatchers(HttpMethod.DELETE,"/property").permitAll()
		.anyRequest().authenticated();
		 */


		/*
		http.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.httpBasic();
		 */
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Hardcoded Only For Test
		auth.inMemoryAuthentication()
				.withUser("testing").password("{noop}testing").roles("USER")
				.and()
				.withUser("admin").password("{noop}admin").roles("ADMIN", "USER");
	}
}
