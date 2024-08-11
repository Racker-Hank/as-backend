package vnu.uet.AppointmentScheduler.repository.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
	@Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 ORDER BY a.createdAt ASC")
	List<Appointment> findAllByPatientId(UUID patientId);

	//	@Query("SELECT a FROM Appointment a WHERE a.status = ?1")
	List<Appointment> findAllByStatus(AssessmentStepStatus status);

	@Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.id = ?2")
	Optional<Appointment> findById(UUID patientId, UUID id);
	//	@Query("SELECT d FROM Appointment d WHERE d.hospital.id = ?1")
	//	List<Appointment> findAllByHospitalId(UUID hospitalId);
	//
	//	@Query("SELECT d FROM Appointment d WHERE d.hospital.id = ?1 AND d.id = ?2")
	//	Optional<Appointment> findById(UUID hospitalId, UUID id);
}
