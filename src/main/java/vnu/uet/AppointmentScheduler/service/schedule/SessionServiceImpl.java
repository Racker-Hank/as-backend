package vnu.uet.AppointmentScheduler.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.dto.schedule.SessionDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.model.schedule.Session;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.repository.schedule.SessionRepository;
import vnu.uet.AppointmentScheduler.service.hospital.RoomService;
import vnu.uet.AppointmentScheduler.service.user.DoctorService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
	private final SessionRepository sessionRepository;
	private final WorkScheduleService workScheduleService;
	private final DoctorService doctorService;
	private final RoomService roomService;

	@Override
	public Session save(Session session) {
		return sessionRepository.save(session);
	}

	@Override
	public Session save(UUID workScheduleId, SessionDTO sessionDTO) {
		try {
			Doctor doctor = doctorService.getOneById(sessionDTO.getDoctorId());
			WorkSchedule workSchedule = workScheduleService.getOneById(sessionDTO.getDoctorId(), workScheduleId);
			Room room = roomService.getOneById(null, sessionDTO.getRoomId());

			Session session = Session.builder()
				.workSchedule(workSchedule)
				.doctor(doctor)
				.workSchedule(workSchedule)
				.room(room)
				.dayOfWeek(sessionDTO.getDayOfWeek())
				.startTime(sessionDTO.getStartTime())
				.endTime(sessionDTO.getEndTime())
				.build();

			return save(session);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public List<Session> saveMany(UUID workScheduleId, List<SessionDTO> sessionDTOs) {
		List<Session> savedSessions = new ArrayList<>();

		for (SessionDTO sessionDTO : sessionDTOs) {
			Session savedSession = save(workScheduleId, sessionDTO);

			savedSessions.add(savedSession);
		}

		return savedSessions;
	}

	@Override
	public List<Session> getAll() {
		return sessionRepository.findAll();
	}

	@Override
	public List<Session> getAllByWorkScheduleId(UUID workScheduleId) {
		return sessionRepository.findAllByWorkScheduleId(workScheduleId);
	}

	@Override
	public List<Session> getAllByRoomId(UUID roomId) {
		return sessionRepository.findAllByRoomId(roomId);
	}

	@Override
	public List<Session> getAllByDoctorId(UUID doctorId) {
		return sessionRepository.findAllByDoctorId(doctorId);
	}

	@Override
	public List<Session> getAllByDayOfWeek(DayOfWeek dayOfWeek) {
		return sessionRepository.findAllByDayOfWeek(dayOfWeek);
	}

	@Override
	public List<Session> getAllByStartTime(LocalTime startTime) {
		return sessionRepository.findAllByStartTime(startTime);
	}

	@Override
	public List<Session> getAllCurrentSessions(UUID departmentId, DayOfWeek dayOfWeek, LocalTime currentTime) {
		return sessionRepository.findAllCurrentSession(departmentId, dayOfWeek, currentTime);
	}

	@Override
	public Session getOneById(UUID workScheduleId, UUID sessionId) {
		if (workScheduleId == null)
			return sessionRepository.findById(sessionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not " +
					"found"));

		return sessionRepository.findById(workScheduleId, sessionId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not " +
				"found"));
	}

	@Override
	public Session updateOne(UUID workScheduleId, UUID sessionId, SessionDTO newSession) {
		try {
			Doctor doctor = doctorService.getOneById(newSession.getDoctorId());
			WorkSchedule workSchedule = workScheduleService.getOneById(newSession.getDoctorId(), newSession.getWorkScheduleId());
			Room room = roomService.getOneById(null, newSession.getRoomId());

			Session session = getOneById(workScheduleId, sessionId);

			session.setDoctor(doctor);
			session.setWorkSchedule(workSchedule);
			session.setRoom(room);
			session.setDayOfWeek(newSession.getDayOfWeek());
			session.setStartTime(newSession.getStartTime());
			session.setEndTime(newSession.getEndTime());

			return save(session);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID workScheduleId, UUID sessionId) {
		try {
			Session workSchedule = getOneById(workScheduleId, sessionId);

			sessionRepository.delete(workSchedule);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteManyByIds(List<UUID> sessionIds) {
		try {
			sessionRepository.deleteAllById(sessionIds);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteAllByWorkScheduleId(UUID workScheduleId) {
		try {
			List<UUID> sessionIds = getAllByWorkScheduleId(workScheduleId)
				.stream()
				.map(Session::getId)
				.toList();

			deleteManyByIds(sessionIds);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
