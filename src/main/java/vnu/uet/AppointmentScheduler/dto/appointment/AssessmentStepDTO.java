package vnu.uet.AppointmentScheduler.dto.appointment;

import lombok.*;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.dto.user.PatientDTO;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentStepDTO {

	private UUID id;

	//	private SessionDTO session;
	//	private UUID sessionId;

	private PatientDTO patient;
	private UUID patientId;

	private int order;

	private AssessmentStepStatus status;

	private Long actualStartTime;

	private Long actualEndTime;

	private Long estimatedStartTime;

	private AssessmentStepDTO followupAppointment;
	private UUID followupAppointmentId;

	private Long followupAppointmentInterval;

	//	public static AssessmentStepDTO convertToDepartmentDTO(Appointment appointment) {
	//		//		Session session = appointment.getSession();
	//
	//		Patient patient = appointment.getPatient();
	//
	//		return AssessmentStepDTO.builder()
	//			.id(appointment.getId())
	//			//			.sessionId(session.getId())
	//			.patient(PatientDTO.convertToPatientDTO(patient))
	//			.patientId(patient.getId())
	//			.order(appointment.getOrder())
	//			.status(appointment.getStatus())
	//			.actualStartTime(appointment.getActualStartTime())
	//			.actualEndTime(appointment.getActualEndTime())
	//			.estimatedStartTime(appointment.getEstimatedStartTime())
	////			.followupAppointment(convertToDepartmentDTO(appointment.getFollowupAppointment()))
	//			.followupAppointmentId(appointment.getFollowupAppointment().getId())
	//			.followupAppointmentInterval(appointment.getFollowupAppointmentInterval())
	//			.build();
	//	}
}
