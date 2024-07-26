package vnu.uet.AppointmentScheduler.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface BaseUserRepository<T extends User> extends JpaRepository<T, UUID> {
	Optional<T> findByIdAndEmailAndUserRole(UUID id, String email, UserRole role);

	Optional<T> findByEmail(String email);

	Optional<T> findByPhone(String phone);
}
