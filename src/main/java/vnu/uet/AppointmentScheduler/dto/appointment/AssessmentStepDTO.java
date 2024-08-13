package vnu.uet.AppointmentScheduler.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.constants.AssessmentType;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;
import vnu.uet.AppointmentScheduler.model.schedule.Session;

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

	private AppointmentDTO appointment;
	private UUID appointmentId;

	private Session session;
	private UUID sessionId;

	@NotBlank
	private Integer orderInQueue;

	@NotBlank
	private AssessmentStepStatus status;

	@NotBlank
	private AssessmentType assessmentType;

	private Long actualStartTime;

	private Long actualEndTime;
	//	private Long createdAt;


	private String notes;

	public static AssessmentStepDTO convertToAssessmentStepDTO(AssessmentStep assessmentStep) {
		Session session = assessmentStep.getSession();

		return AssessmentStepDTO.builder()
			.id(assessmentStep.getId())
			.appointmentId(assessmentStep.getAppointment().getId())
			//			.sessionId(session.getId())
			.orderInQueue(assessmentStep.getOrderInQueue())
			.status(assessmentStep.getStatus())
			.assessmentType(assessmentStep.getAssessmentType())
			.actualStartTime(assessmentStep.getActualStartTime())
			.actualEndTime(assessmentStep.getActualEndTime())
			.notes(assessmentStep.getNotes())
			//			.createdAt(assessmentStep.getCreatedAt())
			.build();
	}
}
