package vnu.uet.AppointmentScheduler.service.appointment;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.dto.appointment.AssessmentStepDTO;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;

import java.util.List;
import java.util.UUID;

@Service
public interface AssessmentStepService {
	AssessmentStep save(AssessmentStep assessmentStep);

	AssessmentStep save(
		UUID patientId,
		UUID appointmentId,
		UUID sessionId,
		AssessmentStepDTO assessmentStepDTO
	);

	List<AssessmentStep> getAll();

	List<AssessmentStep> getAllByAppointmentId(UUID appointmentId);

	List<AssessmentStep> getAllBySessionId(UUID sessionId);

	List<AssessmentStep> getAllByDoctorId(UUID doctorId);

	List<AssessmentStep> getAllByPatientId(UUID appointmentId);

	List<AssessmentStep> getAllByStatus(AssessmentStepStatus status);

	AssessmentStep getOneById(UUID appointmentId, UUID id);

	AssessmentStep updateOne(
		UUID appointmentId,
		UUID assessmentStepId,
		UUID sessionId,
		AssessmentStepDTO newAssessmentStep
	);

	void deleteOne(UUID appointmentId, UUID assessmentStepId);
}
