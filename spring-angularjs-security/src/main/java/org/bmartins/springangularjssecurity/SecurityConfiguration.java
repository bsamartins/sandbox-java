package org.bmartins.springangularjssecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
      http
      	.csrf().disable()
      	.httpBasic()
      .and()
        .authorizeRequests()
          .anyRequest().permitAll()
          .antMatchers("/api/user", "/api/user/logout", "/api/connect").permitAll()
          .antMatchers("/api/**").authenticated()
          ;
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return authenticationManager;	
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationManager;
	}
	
	
}
