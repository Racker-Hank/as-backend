package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.Patient;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.PatientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
	private final PatientRepository patientRepository;

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
		RegisterPatientRequestDTO registerDTO = (RegisterPatientRequestDTO) registerRequestDTO;

		Patient user = Patient.builder()
			.email(registerDTO.getEmail())
			.password(registerDTO.getPassword())
			.userRole(UserRole.PATIENT)
			.createdAt(System.currentTimeMillis())
			.firstName(registerDTO.getFirstName())
			.lastName(registerDTO.getLastName())
			.isActive(registerDTO.isActive())
			.phone(registerDTO.getPhone())
			.build();

		return patientRepository.save(user);
	}

	@Override
	public User updateOne(UUID userId, User user) {
		return null;
	}

	@Override
	public void deleteOne(UUID userId) {

	}
}
