package vnu.uet.AppointmentScheduler.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.DoctorDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.repository.user.DoctorRepository;
import vnu.uet.AppointmentScheduler.service.hospital.DepartmentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
	private final DoctorRepository doctorRepository;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	private final DepartmentService departmentService;

	@Override
	public List<Doctor> getAll() {
		return doctorRepository.findAll();
	}

	@Override
	public Doctor getOneById(UUID id) {
		return doctorRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
	}

	@Override
	public <T extends RegisterRequestDTO> Doctor save(T registerRequestDTO) {
		try {
			RegisterDoctorRequestDTO registerDTO = (RegisterDoctorRequestDTO) registerRequestDTO;

			Department department = departmentService.getOneById(registerDTO.getDepartmentId());

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
				.department(department)
				.build();

			return doctorRepository.save(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public <T extends UserDTO> Doctor updateOne(UUID userId, T userDTO) {
		try {
			DoctorDTO doctorDTO = (DoctorDTO) userDTO;

			String hashedPassword = bcryptPasswordEncoder.encode(doctorDTO.getPassword());

			Department department = departmentService.getOneById(doctorDTO.getDepartmentId());

			Doctor doctor = getOneById(userId);

			doctor.setEmail(doctorDTO.getEmail());
			doctor.setPassword(hashedPassword);
			doctor.setPhone(doctorDTO.getPhone());
			doctor.setUserRole(doctorDTO.getUserRole());
			doctor.setFirstName(doctorDTO.getFirstName());
			doctor.setLastName(doctorDTO.getLastName());
			doctor.setActive(doctorDTO.isActive());
			doctor.setUpdatedAt(System.currentTimeMillis());
			doctor.setGender(doctorDTO.getGender());
			doctor.setDob(doctorDTO.getDob());
			doctor.setAddress(doctorDTO.getAddress());
			doctor.setAvatarUrl(doctorDTO.getAvatarUrl());
			doctor.setDepartment(department);
			doctor.setDoctorType(doctorDTO.getDoctorType());
			doctor.setDegree(doctorDTO.getDegree());
			doctor.setExperience(doctorDTO.getExperience());
			//			doctor.setWorkSchedules(doctorDTO.getWorkSchedules());

			return doctorRepository.save(doctor);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID userId) {
		try {
			Doctor doctor = getOneById(userId);

			doctorRepository.delete(doctor);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
