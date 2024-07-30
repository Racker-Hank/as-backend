package vnu.uet.AppointmentScheduler.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public interface BaseUserService {
	List<User> getAll();

	User getUserById(UUID id);

	<T extends RegisterRequestDTO> User save(T registerRequestDTO);

	User updateOne(UUID userId, User user);

	void deleteOne(UUID userId);
}
