package vnu.uet.AppointmentScheduler.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.schedule.WorkScheduleDTO;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;
import vnu.uet.AppointmentScheduler.service.schedule.WorkScheduleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/work_schedule")
@RequiredArgsConstructor
public class WorkScheduleController {

	private final WorkScheduleService workScheduleService;

	@GetMapping
	public ResponseEntity<List<WorkScheduleDTO>> getAllWorkSchedules(
	) {
		List<WorkSchedule> doctors = workScheduleService.getAll();
		List<WorkScheduleDTO> doctorDTOs = doctors
			.stream()
			.map(WorkScheduleDTO::convertToWorkScheduleDTO)
			.toList();

		return ResponseEntity.ok(doctorDTOs);
	}

	@GetMapping("fetch-by-doctor")
	public ResponseEntity<List<WorkScheduleDTO>> getAllWorkSchedulesByDoctorId(
		@RequestParam("doctor_id") UUID doctorId
	) {
		List<WorkSchedule> doctors = workScheduleService.getAllByDoctorId(doctorId);
		List<WorkScheduleDTO> doctorDTOs = doctors
			.stream()
			.map(WorkScheduleDTO::convertToWorkScheduleDTO)
			.toList();

		return ResponseEntity.ok(doctorDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<WorkScheduleDTO> getWorkScheduleById(
		@PathVariable UUID id,
		@RequestParam("doctor_id") UUID doctorId
	) {
		WorkSchedule workSchedule = workScheduleService.getOneById(doctorId, id);

		return ResponseEntity.ok(WorkScheduleDTO.convertToWorkScheduleDTO(workSchedule));
	}

	@PostMapping
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<WorkScheduleDTO> createWorkSchedule(
		@RequestParam("doctor_id") UUID doctorId,
		@RequestBody WorkScheduleDTO doctorDTO
	) {
		WorkSchedule doctor = workScheduleService.save(doctorId, doctorDTO);

		return new ResponseEntity<>(WorkScheduleDTO.convertToWorkScheduleDTO(doctor),
			HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<WorkScheduleDTO> updateWorkSchedule(
		@RequestParam("doctor_id") UUID doctorId,
		@PathVariable UUID id,
		@RequestBody WorkScheduleDTO scheduleDTO
	) {
		WorkSchedule doctor = workScheduleService.updateOne(
			doctorId,
			id,
			scheduleDTO
		);

		return ResponseEntity.ok(WorkScheduleDTO.convertToWorkScheduleDTO(doctor));
	}

	@DeleteMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<String> deleteWorkSchedule(
		@RequestParam("doctor_id") UUID doctorId,
		@PathVariable UUID id
	) {
		workScheduleService.deleteOne(doctorId, id);

		return ResponseEntity.ok("Successfully deleted work schedule " + id.toString());
	}
}
