package vnu.uet.AppointmentScheduler.dto.appointment;

import lombok.*;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.dto.user.PatientDTO;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;
import vnu.uet.AppointmentScheduler.model.user.Patient;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {

	private UUID id;

	//	private SessionDTO session;
	//	private UUID sessionId;

	private PatientDTO patient;
	//	@NotBlank
	private UUID patientId;

	private int order;

	private AssessmentStepStatus status;

	private Long actualStartTime;

	private Long actualEndTime;

	private Long estimatedStartTime;

	//	private AppointmentDTO followupAppointment;
	private UUID followupAppointmentId;

	private Long followupAppointmentInterval;

	public static AppointmentDTO convertToAppointmentDTO(Appointment appointment) {
		//		Session session = appointment.getSession();

		Patient patient = appointment.getPatient();

		return AppointmentDTO.builder()
			.id(appointment.getId())
			//			.sessionId(session.getId())
			.patient(PatientDTO.convertToPatientDTO(patient))
			.patientId(patient.getId())
			.order(appointment.getOrder())
			.status(appointment.getStatus())
			.actualStartTime(appointment.getActualStartTime())
			.actualEndTime(appointment.getActualEndTime())
			.estimatedStartTime(appointment.getEstimatedStartTime())
			//			.followupAppointment(convertToAppointmentDTO(appointment.getFollowupAppointment()))
			.followupAppointmentId(appointment.getFollowupAppointment().getId())
			.followupAppointmentInterval(appointment.getFollowupAppointmentInterval())
			.build();
	}
}
