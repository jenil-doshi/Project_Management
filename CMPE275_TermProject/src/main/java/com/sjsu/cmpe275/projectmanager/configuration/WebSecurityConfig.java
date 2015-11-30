package com.sjsu.cmpe275.projectmanager.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// public class WebSecurityConfig {

	@Autowired
	DataSource dataSource;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
		web.ignoring().antMatchers("/WEB-INF/views/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/home").access("hasRole('ROLE_ADMIN')
		// or hasRole('ROLE_USER')").and()
		// .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").and()
		// .logout().permitAll().and().csrf().disable();

		http.authorizeRequests().antMatchers("/getRegForm","/register").permitAll();
		http.authorizeRequests().antMatchers("/","/home","/project/**","/task**","/user**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')").and()
				.formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("username")
				.passwordParameter("password").and()
				.exceptionHandling().accessDeniedPage("/403").and().csrf();
//and().logout().logoutSuccessUrl("/login?logout")
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
}