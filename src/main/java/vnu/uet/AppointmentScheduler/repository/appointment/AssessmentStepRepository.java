package vnu.uet.AppointmentScheduler.repository.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssessmentStepRepository extends JpaRepository<AssessmentStep, UUID> {

	@Query("SELECT a FROM AssessmentStep a WHERE a.appointment.id = ?1 ORDER BY a.createdAt ASC")
	List<AssessmentStep> findAllByAppointmentId(UUID appointmentId);

	@Query("SELECT a FROM AssessmentStep a WHERE a.appointment.patient.id = ?1")
	List<AssessmentStep> findAllByPatientId(UUID patientId);

	@Query("SELECT a FROM AssessmentStep a WHERE a.session.id = ?1 ORDER BY a.createdAt ASC")
	List<AssessmentStep> findAllBySessionId(UUID sessionId);

	@Query("SELECT a FROM AssessmentStep a WHERE a.session.doctor.id = ?1")
	List<AssessmentStep> findAllByDoctorId(UUID doctorId);

	@Query("SELECT a FROM AssessmentStep a WHERE a.status = ?1")
	List<AssessmentStep> findAllByStatus(AssessmentStepStatus status);

	@Query("SELECT a FROM AssessmentStep a WHERE a.appointment.id = ?1 AND a.id = ?2")
	Optional<AssessmentStep> findById(UUID appointmentId, UUID id);
}
