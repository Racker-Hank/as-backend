package vnu.uet.AppointmentScheduler.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BaseUserServiceImpl implements BaseUserService {
	private final UserRepository userRepository;

	public BaseUserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(User user) {
		user.setCreatedAt(System.currentTimeMillis());
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return List.of();
	}

	@Override
	public User loadUserById(UUID id) {
		return null;
	}

	@Override
	public User loadUserBy(UUID id, String email, UserRole userRole) {
		return userRepository.findByIdAndEmailAndUserRole(id, email, userRole).orElseThrow(() -> new UsernameNotFoundException(email));
	}

	@Override
	public User updateUser(UUID userId, User user) {
		return null;
	}

	@Override
	public void deleteUser(UUID userId) {

	}

	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
	}
}
