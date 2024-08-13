package vnu.uet.AppointmentScheduler.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.user.PatientDTO;
import vnu.uet.AppointmentScheduler.model.user.Patient;
import vnu.uet.AppointmentScheduler.service.user.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@GetMapping
	@Secured(UserRoleValues.HOSPITAL_ADMIN)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<PatientDTO>> getAllPatients(
		//			@PathVariable("hospital_id") UUID hospitalId
	) {
		List<Patient> patients = patientService.getAll();
		List<PatientDTO> patientDTOs = patients
			.stream()
			.map(PatientDTO::convertToPatientDTO)
			.toList();

		return ResponseEntity.ok(patientDTOs);
	}

	@GetMapping("{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<PatientDTO> getPatientById(
		@PathVariable UUID id
	) {
		Patient patient = patientService.getOneById(id);

		return ResponseEntity.ok(PatientDTO.convertToPatientDTO(patient));
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.PATIENT })
	public ResponseEntity<PatientDTO> updatePatient(
		@PathVariable UUID id,
		@RequestBody PatientDTO patientDTO
	) {
		Patient patient = patientService.updateOne(id, patientDTO);

		return ResponseEntity.ok(PatientDTO.convertToPatientDTO(patient));
	}

	@DeleteMapping("{id}")
	@Secured({ UserRoleValues.PATIENT })
	public ResponseEntity<String> deletePatient(
		@PathVariable UUID id
	) {
		patientService.deleteOne(id);

		return ResponseEntity.ok("Successfully deleted patient " + id.toString());
	}
}
