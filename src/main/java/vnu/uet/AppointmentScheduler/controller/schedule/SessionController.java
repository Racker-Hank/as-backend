package vnu.uet.AppointmentScheduler.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.schedule.SessionDTO;
import vnu.uet.AppointmentScheduler.model.schedule.Session;
import vnu.uet.AppointmentScheduler.service.schedule.SessionService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

	private final SessionService sessionService;

	@GetMapping
	public ResponseEntity<List<SessionDTO>> getAllSessions(
	) {
		List<Session> sessions = sessionService.getAll();
		List<SessionDTO> sessionDTOS = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOS);
	}

	@GetMapping("fetch-by-work-schedule")
	public ResponseEntity<List<SessionDTO>> getAllSessionsByWorkScheduleId(
		@RequestParam("work_schedule_id") UUID workScheduleId
	) {
		List<Session> sessions = sessionService.getAllByWorkScheduleId(workScheduleId);
		List<SessionDTO> sessionDTOs = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("fetch-by-room")
	public ResponseEntity<List<SessionDTO>> getAllSessionsByRoomId(
		@RequestParam("roomId") UUID roomId
	) {
		List<Session> sessions = sessionService.getAllByRoomId(roomId);
		List<SessionDTO> sessionDTOs = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("fetch-by-doctor")
	public ResponseEntity<List<SessionDTO>> getAllSessionsByDoctorId(
		@RequestParam("doctorId") UUID doctorId
	) {
		List<Session> sessions = sessionService.getAllByDoctorId(doctorId);
		List<SessionDTO> sessionDTOs = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("fetch-by-day-of-week")
	public ResponseEntity<List<SessionDTO>> getAllSessionsByDayOfWeek(
		@RequestParam("day_of_week") DayOfWeek dayOfWeek
	) {
		List<Session> sessions = sessionService.getAllByDayOfWeek(dayOfWeek);
		List<SessionDTO> sessionDTOs = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("fetch-by-start-time")
	public ResponseEntity<List<SessionDTO>> getAllSessionsByStartTime(
		@RequestParam("start_time")
		@DateTimeFormat(pattern = "HH:mm:ss")
		LocalTime startTime
	) {
		List<Session> sessions = sessionService.getAllByStartTime(startTime);
		List<SessionDTO> sessionDTOs = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("fetch-current-sessions")
	public ResponseEntity<List<SessionDTO>> getAllCurrentSessions(
		@RequestParam(
			value = "department_id"
			, required = false
		) UUID departmentId,
		@RequestParam("day_of_week") DayOfWeek dayOfWeek,
		@RequestParam("current_time")
		@DateTimeFormat(pattern = "HH:mm:ss")
		LocalTime currentTime
	) {
		List<Session> sessions = sessionService.getAllCurrentSessions(departmentId, dayOfWeek,
			currentTime);
		List<SessionDTO> sessionDTOs = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return ResponseEntity.ok(sessionDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<SessionDTO> getSessionById(
		@PathVariable UUID id,
		@RequestParam("work_schedule_id") UUID workScheduleId
	) {
		Session session = sessionService.getOneById(workScheduleId, id);

		return ResponseEntity.ok(SessionDTO.convertToSessionDTO(session));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<SessionDTO> createSession(
		@RequestParam("work_schedule_id") UUID workScheduleId,
		@RequestBody SessionDTO scheduleDTO
	) {
		Session workSchedule = sessionService.save(workScheduleId, scheduleDTO);

		return new ResponseEntity<>(SessionDTO.convertToSessionDTO(workSchedule),
			HttpStatus.CREATED);
	}

	@PostMapping("create-many")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<List<SessionDTO>> createManySessions(
		@RequestParam("work_schedule_id") UUID workScheduleId,
		@RequestBody List<SessionDTO> scheduleDTO
	) {
		List<Session> sessions = sessionService.saveMany(workScheduleId, scheduleDTO);
		List<SessionDTO> sessionDTOS = sessions
			.stream()
			.map(SessionDTO::convertToSessionDTO)
			.toList();

		return new ResponseEntity<>(sessionDTOS, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<SessionDTO> updateSession(
		@RequestParam("work_schedule_id") UUID workScheduleId,
		@PathVariable UUID id,
		@RequestBody SessionDTO scheduleDTO
	) {
		Session workSchedule = sessionService.updateOne(
			workScheduleId,
			id,
			scheduleDTO
		);

		return ResponseEntity.ok(SessionDTO.convertToSessionDTO(workSchedule));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<String> deleteSession(
		@RequestParam("work_schedule_id") UUID workScheduleId,
		@PathVariable UUID id
	) {
		sessionService.deleteOne(workScheduleId, id);

		return ResponseEntity.ok("Successfully deleted session " + id.toString());
	}
}
