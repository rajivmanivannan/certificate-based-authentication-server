package com.rm.cba.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CertificateBasedAuthenticationApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(CertificateBasedAuthenticationApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
				.and()
				.x509()
				.subjectPrincipalRegex("CN=(.*?)(?:,|$)")
				.userDetailsService(userDetailsService());
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			if (username.equals("Alice") || username.equals("Bob")) {
				return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
			}
			throw new UsernameNotFoundException("User not found");
		};
	}
}
