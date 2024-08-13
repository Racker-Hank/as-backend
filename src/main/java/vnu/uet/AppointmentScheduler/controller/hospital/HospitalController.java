package vnu.uet.AppointmentScheduler.controller.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.HospitalDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;
import vnu.uet.AppointmentScheduler.service.hospital.HospitalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {
	private final HospitalService hospitalService;

	@GetMapping
	public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
		List<Hospital> hospitals = hospitalService.getAll();

		List<HospitalDTO> hospitalDTOs = hospitals
			.stream()
			.map(HospitalDTO::convertToHospitalDTO)
			.toList();

		return ResponseEntity.ok(hospitalDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<HospitalDTO> getHospitalById(
		@PathVariable UUID id
	) {
		Hospital hospital = hospitalService.getOneById(id);

		return ResponseEntity.ok(HospitalDTO.convertToHospitalDTO(hospital));
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<HospitalDTO> updateHospital(
		//		@AuthenticationPrincipal User user,
		@PathVariable UUID id,
		@RequestBody HospitalDTO hospitalDTO
	) {
		Hospital hospital = hospitalService.updateOne(id, hospitalDTO);

		return ResponseEntity.ok(HospitalDTO.convertToHospitalDTO(hospital));
	}
}
