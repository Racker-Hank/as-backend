package vnu.uet.AppointmentScheduler.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, UUID> {
	@Query("SELECT s FROM WorkSchedule s WHERE s.doctor.id = ?1")
	List<WorkSchedule> findAllByDoctorId(UUID doctorId);

	@Query("SELECT s FROM WorkSchedule s WHERE s.doctor.id = ?1 AND s.id = ?2")
	Optional<WorkSchedule> findById(UUID doctorId, UUID scheduleId);
}
