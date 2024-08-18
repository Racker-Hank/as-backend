package vnu.uet.AppointmentScheduler.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.model.schedule.Session;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

	@Query("SELECT s " +
		"FROM Session s " +
		"WHERE s.workSchedule.id = ?1 " +
		"ORDER BY s.dayOfWeek ASC, " +
		"s.startTime ASC"
	)
	List<Session> findAllByWorkScheduleId(UUID workScheduleId);

	@Query("SELECT s " +
		"FROM Session s " +
		"WHERE s.room.id = ?1 " +
		"ORDER BY s.dayOfWeek ASC, " +
		"s.startTime ASC"
	)
	List<Session> findAllByRoomId(UUID roomId);

	@Query("SELECT s " +
		"FROM Session s " +
		"WHERE s.doctor.id = ?1 " +
		"ORDER BY s.dayOfWeek ASC, " +
		"s.startTime ASC"
	)
	List<Session> findAllByDoctorId(UUID doctorId);

	@Query("SELECT s " +
		"FROM Session s " +
		"WHERE s.dayOfWeek = ?1 " +
		"ORDER BY s.startTime ASC"
	)
	List<Session> findAllByDayOfWeek(DayOfWeek dayOfWeek);

	@Query("SELECT s " +
		"FROM Session s " +
		"WHERE s.startTime = ?1 "
	)
	List<Session> findAllByStartTime(LocalTime startTime);

	@Query("SELECT s " +
		"FROM Session s " +
		"WHERE s.dayOfWeek = ?2 " +
		"AND s.doctor.department.id = ?1 " +
		"AND ?3 BETWEEN s.startTime AND s.endTime "
		//		+ "ORDER BY s.startTime ASC"
	)
	List<Session> findAllCurrentSession(UUID departmentId, DayOfWeek dayOfWeek, LocalTime currentTime);

	@Query("SELECT s FROM Session s WHERE s.workSchedule.id = ?1 AND s.id = ?2")
	Optional<Session> findById(UUID workScheduleId, UUID sessionId);
}
