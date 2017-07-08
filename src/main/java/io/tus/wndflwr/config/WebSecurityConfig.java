package io.tus.wndflwr.config;

import io.tus.wndflwr.config.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.and().authorizeRequests().antMatchers("/console/**").permitAll() // TODO to be removed
				.and().authorizeRequests().antMatchers("/img/**").permitAll()
				.and().authorizeRequests().antMatchers("/css/**").permitAll()
				.and().authorizeRequests().antMatchers("/js/**").permitAll()
				.and().authorizeRequests().antMatchers("/**/favicon.ico").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.failureUrl("/login")
				.successForwardUrl("/admin")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
			.logout()
				.permitAll()
				.and();

		http.csrf().disable(); // TODO to be removed
		http.headers().frameOptions().disable(); // TODO to be removed
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsServiceImpl)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
