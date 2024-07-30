package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterHospitalAdminRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.HospitalAdmin;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.HospitalAdminRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HospitalAdminServiceImpl implements HospitalAdminService {
	private final HospitalAdminRepository hospitalAdminRepository;

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
		RegisterHospitalAdminRequestDTO registerDTO = (RegisterHospitalAdminRequestDTO) registerRequestDTO;
		HospitalAdmin user = HospitalAdmin.builder()
			.email(registerDTO.getEmail())
			.password(registerDTO.getPassword())
			.userRole(UserRole.HOSPITAL_ADMIN)
			.firstName(registerDTO.getFirstName())
			.lastName(registerDTO.getLastName())
			.isActive(registerDTO.isActive())
			.createdAt(System.currentTimeMillis())
			.phone(registerDTO.getPhone())
			.build();

		return hospitalAdminRepository.save(user);
	}

	@Override
	public User updateOne(UUID userId, User user) {
		return null;
	}

	@Override
	public void deleteOne(UUID userId) {

	}
}
