package vnu.uet.AppointmentScheduler.middleware.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	private final UserRepository userRepository;

	@Override
	public User getUserBy(UUID id, String email, UserRole userRole) {
		return userRepository.findByIdAndEmailAndUserRole(id, email, userRole)
			.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
