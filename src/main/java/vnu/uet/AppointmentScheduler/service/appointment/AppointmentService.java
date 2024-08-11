package vnu.uet.AppointmentScheduler.service.appointment;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.dto.appointment.AppointmentDTO;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;

import java.util.List;
import java.util.UUID;

@Service
public interface AppointmentService {
	Appointment save(Appointment appointment);

	Appointment save(UUID patientId, AppointmentDTO appointmentDTO);

	List<Appointment> getAll();

	//	List<Appointment> getAllBySessionId(UUID sessionId);
	//	List<Appointment> getAllByDoctorId(UUID doctorId);
	List<Appointment> getALLByPatientId(UUID patientId);

	List<Appointment> getAllByStatus(AssessmentStepStatus status);

	Appointment getOneById(UUID patientId, UUID id);

	Appointment updateOne(UUID patientId, UUID appointmentId, AppointmentDTO appointment);
	//	Appointment updateOneStatus(UUID patientId, UUID appointmentId, AssessmentStepStatus newStatus);

	void deleteOne(UUID patientId, UUID appointmentId);
}
