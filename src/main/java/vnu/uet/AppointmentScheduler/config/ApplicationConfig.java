package vnu.uet.AppointmentScheduler.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vnu.uet.AppointmentScheduler.middleware.auth.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final CustomUserDetailsService customUserDetailsService;

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
		HttpSecurity http
	) throws Exception {
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		auth
			.userDetailsService(customUserDetailsService)
			.passwordEncoder(bcryptPasswordEncoder());
		return auth.build();
	}
}
