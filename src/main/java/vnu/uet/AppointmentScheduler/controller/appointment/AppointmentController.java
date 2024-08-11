package vnu.uet.AppointmentScheduler.controller.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.appointment.AppointmentDTO;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;
import vnu.uet.AppointmentScheduler.service.appointment.AppointmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

	private final AppointmentService appointmentService;

	@GetMapping
	public ResponseEntity<List<AppointmentDTO>> getAllAppointments(
	) {
		List<Appointment> appointments = appointmentService.getAll();
		List<AppointmentDTO> appointmentDTOs = appointments
			.stream()
			.map(AppointmentDTO::convertToAppointmentDTO)
			.toList();

		return ResponseEntity.ok(appointmentDTOs);
	}

	@GetMapping("fetch-by-patient")
	public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsByPatientId(
		@RequestParam("patient_id") UUID patientId
	) {
		List<Appointment> appointments = appointmentService.getALLByPatientId(patientId);
		List<AppointmentDTO> appointmentDTOs = appointments
			.stream()
			.map(AppointmentDTO::convertToAppointmentDTO)
			.toList();

		return ResponseEntity.ok(appointmentDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<AppointmentDTO> getAppointmentById(
		@PathVariable UUID id,
		@RequestParam(
			value = "patient_id",
			required = false
		) UUID patientId
	) {
		Appointment appointment = appointmentService.getOneById(patientId, id);

		return ResponseEntity.ok(AppointmentDTO.convertToAppointmentDTO(appointment));
	}

	@PostMapping
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<AppointmentDTO> createAppointment(
		@RequestParam("patient_id") UUID patientId,
		@RequestBody AppointmentDTO appointmentDTO
	) {
		Appointment appointment = appointmentService.save(patientId, appointmentDTO);

		return new ResponseEntity<>(AppointmentDTO.convertToAppointmentDTO(appointment), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<AppointmentDTO> updateAppointment(
		@RequestParam("patient_id") UUID patientId,
		@PathVariable UUID id,
		@RequestBody AppointmentDTO appointmentDTO
	) {
		Appointment appointment = appointmentService.updateOne(patientId, id, appointmentDTO);

		return ResponseEntity.ok(AppointmentDTO.convertToAppointmentDTO(appointment));
	}

	@DeleteMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<String> deleteAppointment(
		@RequestParam("patient_id") UUID patientId,
		@PathVariable UUID id
	) {
		appointmentService.deleteOne(patientId, id);

		return ResponseEntity.ok("Successfully deleted appointment " + id.toString());
	}
}
