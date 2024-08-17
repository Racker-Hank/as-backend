package vnu.uet.AppointmentScheduler.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vnu.uet.AppointmentScheduler.middleware.auth.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(management ->
				management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.addFilterBefore(
				jwtAuthFilter,
				UsernamePasswordAuthenticationFilter.class
			)
			.authorizeHttpRequests(requests ->
					requests
						.requestMatchers(HttpMethod.GET, UnsecuredEndpointPatterns.GET_METHOD.retrieve()).permitAll()
						.requestMatchers(UnsecuredEndpointPatterns.NON_GET_METHODS.retrieve()).permitAll()
						.anyRequest().authenticated()
			);
		return http.build();
	}
}
