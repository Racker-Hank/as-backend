package vnu.uet.AppointmentScheduler.service.schedule;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.schedule.SessionDTO;
import vnu.uet.AppointmentScheduler.model.schedule.Session;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public interface SessionService {
	Session save(Session session);

	Session save(UUID workScheduleId, SessionDTO sessionDTO);

	List<Session> saveMany(UUID workScheduleId, List<SessionDTO> sessionDTOs);

	List<Session> getAll();

	List<Session> getAllByWorkScheduleId(UUID workScheduleId);

	List<Session> getAllByRoomId(UUID roomId);

	List<Session> getAllByDoctorId(UUID doctorId);

	List<Session> getAllByDayOfWeek(DayOfWeek dayOfWeek);

	List<Session> getAllByStartTime(LocalTime startTime);

	List<Session> getAllCurrentSessions(DayOfWeek dayOfWeek, LocalTime currentTime);

	Session getOneById(UUID workScheduleId, UUID sessionId);

	Session updateOne(UUID workScheduleId, UUID sessionId, SessionDTO newSchedule);

	void deleteOne(UUID workScheduleId, UUID sessionId);

	void deleteManyByIds(List<UUID> sessionIds);

	void deleteAllByWorkScheduleId(UUID workScheduleId);
}
