package vnu.uet.AppointmentScheduler.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class BaseUserServiceImpl implements BaseUserService<User> {
	private final UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return List.of();
	}

	@Override
	public User getUserById(UUID id) {
		return null;
	}

	@Override
	public void deleteOne(UUID userId) {
	}

	@Override
	public <T extends RegisterRequestDTO> User save(T registerRequestDTO) {
		return null;
	}

	@Override
	public <T extends UserDTO> User updateOne(UUID userId, T userDTO) {
		//		User user = getUserById(userId);
		//
		//		user.setEmail(userDTO.getEmail());
		//		user.setPassword(userDTO.getPassword());
		//		user.setPhone(userDTO.getPhone());
		//		user.setUserRole(userDTO.getUserRole());
		//		user.setFirstName(userDTO.getFirstName());
		//		user.setLastName(userDTO.getLastName());
		//		user.setActive(userDTO.isActive());
		//		user.setUpdatedAt(System.currentTimeMillis());
		//		user.setGender(userDTO.getGender());
		//		user.setDob(userDTO.getDob());
		//		user.setAddress(userDTO.getAddress());
		//		user.setAvatarUrl(userDTO.getAvatarUrl());
		//
		//		return userRepository.save(user);

		return null;
	}
}
