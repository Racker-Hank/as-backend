package vnu.uet.AppointmentScheduler.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vnu.uet.AppointmentScheduler.config.security.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserDetailsServiceImpl userDetailServiceImpl;

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
	                                                   BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		auth
			.userDetailsService(userDetailServiceImpl)
			.passwordEncoder(bCryptPasswordEncoder);
		return auth.build();
	}
}
