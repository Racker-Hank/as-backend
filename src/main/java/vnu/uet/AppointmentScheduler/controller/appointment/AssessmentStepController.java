package vnu.uet.AppointmentScheduler.controller.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.appointment.AssessmentStepDTO;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;
import vnu.uet.AppointmentScheduler.service.appointment.AssessmentStepService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assessment-step")
@RequiredArgsConstructor
public class AssessmentStepController {

	private final AssessmentStepService assessmentStepService;

	@GetMapping
	public ResponseEntity<List<AssessmentStepDTO>> getAllAssessmentSteps(
	) {
		List<AssessmentStep> assessmentSteps = assessmentStepService.getAll();
		List<AssessmentStepDTO> assessmentStepDTOs = assessmentSteps
			.stream()
			.map(AssessmentStepDTO::convertToAssessmentStepDTO)
			.toList();

		return ResponseEntity.ok(assessmentStepDTOs);
	}

	@GetMapping("fetch-by-appointment")
	public ResponseEntity<List<AssessmentStepDTO>> getAllAssessmentStepsByAppointmentId(
		@RequestParam("appointment_id") UUID appointmentId
	) {
		List<AssessmentStep> assessmentSteps =
			assessmentStepService.getAllByAppointmentId(appointmentId);
		List<AssessmentStepDTO> assessmentStepDTOs = assessmentSteps
			.stream()
			.map(AssessmentStepDTO::convertToAssessmentStepDTO)
			.toList();

		return ResponseEntity.ok(assessmentStepDTOs);
	}

	@GetMapping("fetch-by-session")
	public ResponseEntity<List<AssessmentStepDTO>> getAllAssessmentStepsBySessionId(
		@RequestParam("session_id") UUID sessionId
	) {
		List<AssessmentStep> assessmentSteps =
			assessmentStepService.getAllBySessionId(sessionId);
		List<AssessmentStepDTO> assessmentStepDTOs = assessmentSteps
			.stream()
			.map(AssessmentStepDTO::convertToAssessmentStepDTO)
			.toList();

		return ResponseEntity.ok(assessmentStepDTOs);
	}

	@GetMapping("fetch-by-doctor")
	public ResponseEntity<List<AssessmentStepDTO>> getAllAssessmentStepsByDoctorId(
		@RequestParam("doctor_id") UUID doctorId
	) {
		List<AssessmentStep> assessmentSteps =
			assessmentStepService.getAllByDoctorId(doctorId);
		List<AssessmentStepDTO> assessmentStepDTOs = assessmentSteps
			.stream()
			.map(AssessmentStepDTO::convertToAssessmentStepDTO)
			.toList();

		return ResponseEntity.ok(assessmentStepDTOs);
	}

	@GetMapping("fetch-by-patient")
	public ResponseEntity<List<AssessmentStepDTO>> getAllAssessmentStepsByPatientId(
		@RequestParam("patient_id") UUID patientId
	) {
		List<AssessmentStep> assessmentSteps =
			assessmentStepService.getAllByPatientId(patientId);
		List<AssessmentStepDTO> assessmentStepDTOs = assessmentSteps
			.stream()
			.map(AssessmentStepDTO::convertToAssessmentStepDTO)
			.toList();

		return ResponseEntity.ok(assessmentStepDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<AssessmentStepDTO> getAssessmentStepById(
		@PathVariable UUID id,
		@RequestParam(
			value = "appointment_id",
			required = false
		) UUID appointmentId
	) {
		AssessmentStep assessmentStep = assessmentStepService.getOneById(appointmentId, id);

		return ResponseEntity.ok(AssessmentStepDTO.convertToAssessmentStepDTO(assessmentStep));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<AssessmentStepDTO> createAssessmentStep(
		@RequestParam(
			value = "patient_id",
			required = false
		) UUID patientId,
		@RequestParam("appointment_id") UUID appointmentId,
		@RequestParam("session_id") UUID sessionId,
		@RequestBody AssessmentStepDTO assessmentStepDTO
	) {
		AssessmentStep assessmentStep = assessmentStepService.save(
			patientId,
			appointmentId,
			sessionId,
			assessmentStepDTO
		);

		return new ResponseEntity<>(AssessmentStepDTO.convertToAssessmentStepDTO(assessmentStep),
			HttpStatus.CREATED);
	}

	@PutMapping("{id}")
//	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<AssessmentStepDTO> updateAssessmentStep(
		@RequestParam("appointment_id") UUID appointmentId,
		@RequestParam("session_id") UUID sessionId,
		@PathVariable UUID id,
		@RequestBody AssessmentStepDTO assessmentStepDTO
	) {
		AssessmentStep assessmentStep = assessmentStepService.updateOne(
			appointmentId,
			sessionId,
			id,
			assessmentStepDTO
		);

		return ResponseEntity.ok(AssessmentStepDTO.convertToAssessmentStepDTO(assessmentStep));
	}

	@DeleteMapping("{id}")
//	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<String> deleteAssessmentStep(
		@RequestParam("appointment_id") UUID appointmentId,
		@PathVariable UUID id
	) {
		assessmentStepService.deleteOne(appointmentId, id);

		return ResponseEntity.ok("Successfully deleted assessmentStep " + id.toString());
	}
}
