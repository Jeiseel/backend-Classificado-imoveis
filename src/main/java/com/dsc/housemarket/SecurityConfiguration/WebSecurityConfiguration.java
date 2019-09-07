package com.dsc.housemarket.SecurityConfiguration;

import com.dsc.housemarket.Services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import static com.dsc.housemarket.SecurityConfiguration.SecurityParameters.CSRF_NAME;
import static com.dsc.housemarket.SecurityConfiguration.SecurityParameters.SIGNUP_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception{

		// Cross-Site Request Forgery Token
		http
				.addFilterAfter(new CSRFTokenFilter(), CsrfFilter.class)
				.csrf()
					.csrfTokenRepository(csrfTokenRepository())
					.ignoringAntMatchers("/login")
					.ignoringAntMatchers("/api/signup")

				.and()
				.authorizeRequests()
					.antMatchers("/**");


		// Cross-Origin Resource Sharing
		http.cors().disable();

		// Request Authorization
		http.authorizeRequests()
				// Login Page
				.antMatchers(HttpMethod.POST, SIGNUP_URL).permitAll()
				.antMatchers(HttpMethod.GET, SIGNUP_URL).permitAll()

				// User Endpoint
				.antMatchers(HttpMethod.GET, "**/user/**").permitAll()
				.antMatchers(HttpMethod.POST, "**/user/**").authenticated()
				.antMatchers(HttpMethod.PUT, "**/user/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "**/user/**").authenticated()

				// Property Endpoint
				.antMatchers(HttpMethod.GET, "/property/**").permitAll()
				.antMatchers(HttpMethod.POST, "/property/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/property/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "/property/**").authenticated()

				// Feature Endpoint
				.antMatchers(HttpMethod.GET, "/feature/**").permitAll()
				.antMatchers(HttpMethod.POST, "/feature/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/feature/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "/feature/**").authenticated()

				.and()

				// Authentication and Authorization Middleware
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));

		// Permit Access h2-console via Browser
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(CSRF_NAME);
		return repository;
	}
}
