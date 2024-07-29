package vnu.uet.AppointmentScheduler.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public interface BaseUserService extends UserDetailsService {
	User saveUser(User user);

	List<User> getAllUsers();

	User loadUserById(UUID id);

	User updateUser(UUID userId, User user);

	void deleteUser(UUID userId);

	User loadUserBy(UUID id, String email, UserRole userRole);
}
