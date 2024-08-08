package vnu.uet.AppointmentScheduler.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.PatientDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.Patient;
import vnu.uet.AppointmentScheduler.repository.user.PatientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
	private final PatientRepository patientRepository;
	private final BaseUserServiceImpl userService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	@Override
	public List<Patient> getAll() {
		return patientRepository.findAll();
	}

	@Override
	public Patient getUserById(UUID id) {
		return patientRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
	}

	@Override
	public <T extends RegisterRequestDTO> Patient save(T registerRequestDTO) {
		try {
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
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public <T extends UserDTO> Patient updateOne(UUID userId, T userDTO) {
		try {
			//			Patient patient = (Patient) userService.updateOne(userId, userDTO);

			PatientDTO patientDTO = (PatientDTO) userDTO;

			Patient patient = getUserById(userId);

			String hashedPassword = bcryptPasswordEncoder.encode(patientDTO.getPassword());

			patient.setEmail(patientDTO.getEmail());
			patient.setPassword(hashedPassword);
			patient.setPhone(patientDTO.getPhone());
			patient.setUserRole(patientDTO.getUserRole());
			patient.setFirstName(patientDTO.getFirstName());
			patient.setLastName(patientDTO.getLastName());
			patient.setActive(patientDTO.isActive());
			patient.setUpdatedAt(System.currentTimeMillis());
			patient.setGender(patientDTO.getGender());
			patient.setDob(patientDTO.getDob());
			patient.setAddress(patientDTO.getAddress());
			patient.setAvatarUrl(patientDTO.getAvatarUrl());

			return patientRepository.save(patient);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID userId) {
		try {
			Patient patient = getUserById(userId);

			patientRepository.delete(patient);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
