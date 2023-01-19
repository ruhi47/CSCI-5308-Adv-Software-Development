package edu.dal.eauction.userManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
			.authorizeRequests()
			.antMatchers("/", "/index", "/userRegistration", "/eAuctionHome", "/user/register",
						"/user/forgotPassword", "/user/recoverCredentials", "/bid/place").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/userLogin").permitAll()
			.loginProcessingUrl("/user/login").defaultSuccessUrl("/user/loginSuccess")
			.and().logout()
			.logoutUrl("/logout").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(getAuthenticationProvider());
	}

	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}

}
