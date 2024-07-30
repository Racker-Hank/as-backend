package vnu.uet.AppointmentScheduler.middleware.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {
	User getUserBy(UUID id, String email, UserRole userRole);
}
