package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BaseUserServiceImpl implements BaseUserService {

	@Override
	public List<User> getAll() {
		return List.of();
	}

	@Override
	public User getUserById(UUID id) {
		return null;
	}

	@Override
	public <T extends RegisterRequestDTO> User save(T registerRequestDTO) {
		return null;
	}

	@Override
	public User updateOne(UUID userId, User user) {
		return null;
	}

	@Override
	public void deleteOne(UUID userId) {

	}
}
