package vnu.uet.AppointmentScheduler.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.middleware.auth.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
		throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(requests ->
				requests
					.requestMatchers(
						"/auth/login",
						"/auth/test"
					).permitAll()
					.requestMatchers("/auth/register/doctor")
					.hasAuthority(UserRole.HOSPITAL_ADMIN.toString())
					.anyRequest().authenticated()
			)
			.sessionManagement(management ->
				management.sessionCreationPolicy(
					SessionCreationPolicy.STATELESS
				)
			)
			.addFilterBefore(
				jwtAuthFilter,
				UsernamePasswordAuthenticationFilter.class
			);
		return http.build();
	}
}
