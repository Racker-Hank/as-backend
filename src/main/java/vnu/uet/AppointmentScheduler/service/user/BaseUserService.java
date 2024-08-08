package vnu.uet.AppointmentScheduler.service.user;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.List;
import java.util.UUID;

@Service
//@Transactional
public interface BaseUserService<U extends User> {
	List<U> getAll();

	U getUserById(UUID id);

	<T extends RegisterRequestDTO> U save(T registerRequestDTO);

	<T extends UserDTO> U updateOne(UUID userId, T userDTO);

	void deleteOne(UUID userId);
}
