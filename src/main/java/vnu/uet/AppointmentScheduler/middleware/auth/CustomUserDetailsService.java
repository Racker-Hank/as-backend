package vnu.uet.AppointmentScheduler.middleware.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.UUID;

@Service
public interface CustomUserDetailsService extends UserDetailsService {
	User getUserBy(UUID id, String email, UserRole userRole);
}
