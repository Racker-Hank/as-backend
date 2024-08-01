package vnu.uet.AppointmentScheduler.controller.hospital;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.HospitalDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;
import vnu.uet.AppointmentScheduler.service.hospital.HospitalService;

import java.util.UUID;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {
	private final HospitalService hospitalService;
	private final EntityManager entityManager;


	@GetMapping("{id}")
	public ResponseEntity<Hospital> getHospitalById(@PathVariable UUID id) {
		Hospital hospital = hospitalService.getHospitalById(id);

		return ResponseEntity.ok(hospital);
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<Hospital> updateHospital(
		//		@AuthenticationPrincipal User user,
		@PathVariable UUID id,
		@RequestBody HospitalDTO hospitalDTO
	) {
		return ResponseEntity.ok(hospitalService.updateOne(id, hospitalDTO));
	}
}
