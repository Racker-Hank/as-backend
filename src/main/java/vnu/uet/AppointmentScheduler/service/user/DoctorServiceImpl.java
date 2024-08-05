package vnu.uet.AppointmentScheduler.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.repository.user.DoctorRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
	private final DoctorRepository doctorRepository;

	@Override
	public List<Doctor> getAll() {
		return doctorRepository.findAll();
	}

	@Override
	public Doctor getUserById(UUID id) {
		return doctorRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not " +
				"found"));
	}

	@Override
	public <T extends RegisterRequestDTO> Doctor save(T registerRequestDTO) {
		try {
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
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public <T extends UserDTO> Doctor updateOne(UUID userId, T userDTO) {
		try {
			Doctor doctor = getUserById(userId);

			//		doctor.set

			return doctorRepository.save(doctor);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID userId) {
		try {
			doctorRepository.deleteById(userId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
