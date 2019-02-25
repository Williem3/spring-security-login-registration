package com.luv2code.springsecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.luv2code.springsecurity.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our security data source
    @Autowired
    private UserService userService;
	
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// .antMatchers for assigning access to mapping via ROLE
			.antMatchers("/").hasRole("USER")
			.antMatchers("/leaders/**").hasRole("MANAGER")
			.antMatchers("/systems/**").hasRole("ADMIN")
			.and() // use and to allow additional arguments to http
			.formLogin() // require Login form to be presented to user
				.loginPage("/showMyLoginPage") // declare the loginPage mapping
				.loginProcessingUrl("/authenticateTheUser") // spring automates, but necessary to declare the mapping
				.successHandler(customAuthenticationSuccessHandler) // necessary if adding customer logic
				.permitAll() // all users are permitted access to the login page.
			.and()
			.logout().permitAll()// allow all users to logout
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}

	// bcrypt bean definition
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

   		// declare new DaoAuthenticationProvider object
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

		// use object to set UserDetailsService and PasswordEncoder
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt

		// return the object
		return auth;
	}
	  
}






