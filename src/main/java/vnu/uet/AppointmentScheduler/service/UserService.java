package vnu.uet.AppointmentScheduler.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnu.uet.AppointmentScheduler.model.User;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public interface UserService {
	User saveUser(User user);

	List<User> getAllUsers();

	User getUserById(UUID id);

	User updateUser(UUID userId, User user);

	void deleteUser(UUID userId);
}
