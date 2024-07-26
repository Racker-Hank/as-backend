package vnu.uet.AppointmentScheduler.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetails loadUserBy(UUID id, String email, UserRole userRole) {
		return userRepository.findByIdAndEmailAndUserRole(id, email, userRole)
			.map(user ->
				UserDetailsImpl.builder()
					.email(user.getEmail())
					.password(user.getPassword())
					.userRole(user.getUserRole())
					.build()
			)
			.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		return userRepository
			.findByEmail(email)
			.map(user ->
				UserDetailsImpl.builder()
					.email(user.getEmail())
					.password(user.getPassword())
					.userRole(user.getUserRole())
					.build()
			)
			.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return loadUserByEmail(email);
	}
}
