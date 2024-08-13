package vnu.uet.AppointmentScheduler.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.user.DoctorDTO;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.service.user.DoctorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorService doctorService;

	@GetMapping
	//	@Secured(UserRoleValues.HOSPITAL_ADMIN)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<DoctorDTO>> getAllDoctors(
	) {
		List<Doctor> doctors = doctorService.getAll();
		List<DoctorDTO> doctorDTOs = doctors
			.stream()
			.map(DoctorDTO::convertToDoctorDTO)
			.toList();

		return ResponseEntity.ok(doctorDTOs);
	}

	@GetMapping("{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<DoctorDTO> getDoctorById(
		@PathVariable UUID id
	) {
		Doctor doctor = doctorService.getOneById(id);

		return ResponseEntity.ok(DoctorDTO.convertToDoctorDTO(doctor));
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN, UserRoleValues.DOCTOR })
	public ResponseEntity<DoctorDTO> updateDoctor(
		@PathVariable UUID id,
		@RequestBody DoctorDTO doctorDTO
	) {
		Doctor doctor = doctorService.updateOne(id, doctorDTO);

		return ResponseEntity.ok(DoctorDTO.convertToDoctorDTO(doctor));
	}

	@DeleteMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<String> deleteDoctor(
		@PathVariable UUID id
	) {
		doctorService.deleteOne(id);

		return ResponseEntity.ok("Successfully deleted doctor " + id.toString());
	}
}
