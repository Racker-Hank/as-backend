package vnu.uet.AppointmentScheduler.model.appointment;

import jakarta.persistence.*;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.constants.AssessmentType;
import vnu.uet.AppointmentScheduler.model.schedule.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assessment_step")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentStep {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;

	//	@OneToOne
	//	@JoinColumn(name = "session_id")
	//	private Session session;

	@ManyToOne
	@JoinColumn(name = "session_id")
	private Session session;

	@Column(name = "order_in_queue", nullable = false)
	private Integer orderInQueue;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private AssessmentStepStatus status;

	@Column(name = "examination_type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private AssessmentType assessmentType;

	@Column(name = "actual_start_time")
	private Long actualStartTime;

	@Column(name = "actual_end_time")
	private Long actualEndTime;

	@Column(columnDefinition = "TEXT")
	private String notes;

	@Column(name = "created_at", updatable = false, nullable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;

	@OneToMany(mappedBy = "assessmentStep", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FeedbackDoctor> feedbackDoctors = new ArrayList<>();

	@OneToMany(mappedBy = "assessmentStep", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssessmentStepAttachment> assessmentStepAttachments = new ArrayList<>();
}
