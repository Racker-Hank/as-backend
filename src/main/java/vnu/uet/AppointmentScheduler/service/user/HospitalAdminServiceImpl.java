package vnu.uet.AppointmentScheduler.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterHospitalAdminRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.HospitalAdmin;
import vnu.uet.AppointmentScheduler.repository.user.HospitalAdminRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalAdminServiceImpl implements HospitalAdminService {
	private final HospitalAdminRepository hospitalAdminRepository;

	@Override
	public List<HospitalAdmin> getAll() {
		return hospitalAdminRepository.findAll();
	}

	@Override
	public HospitalAdmin getOneById(UUID id) {
		return hospitalAdminRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital admin not found"));
	}

	@Override
	public <T extends RegisterRequestDTO> HospitalAdmin save(T registerRequestDTO) {
		try {
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
				.hospital(registerDTO.getHospital())
				.build();

			return hospitalAdminRepository.save(user);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public <T extends UserDTO> HospitalAdmin updateOne(UUID userId, T userDTO) {
		try {
			HospitalAdmin hospitalAdmin = getOneById(userId);

			//		doctor.set

			return hospitalAdminRepository.save(hospitalAdmin);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID userId) {
		try {
			hospitalAdminRepository.deleteById(userId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
