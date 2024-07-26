package vnu.uet.AppointmentScheduler.service;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

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
	public User getUserById(UUID id) {
		return null;
	}

	@Override
	public User updateUser(UUID userId, User user) {
		return null;
	}

	@Override
	public void deleteUser(UUID userId) {

	}
}
