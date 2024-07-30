package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.DoctorRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
	private final DoctorRepository doctorRepository;

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
		RegisterDoctorRequestDTO registerDTO = (RegisterDoctorRequestDTO) registerRequestDTO;
		Doctor user = Doctor.builder()
			.email(registerDTO.getEmail())
			.password(registerDTO.getPassword())
			.userRole(UserRole.DOCTOR)
			.doctorType(registerDTO.getDoctorType())
			.firstName(registerDTO.getFirstName())
			.lastName(registerDTO.getLastName())
			.isActive(registerDTO.isActive())
			.createdAt(System.currentTimeMillis())
			.phone(registerDTO.getPhone())
			.build();

		return doctorRepository.save(user);
	}

	@Override
	public User updateOne(UUID userId, User user) {
		return null;
	}

	@Override
	public void deleteOne(UUID userId) {

	}
}
