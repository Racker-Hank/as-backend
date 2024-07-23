package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assessment_step")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentStep {

	public enum Status {
		IN_QUEUE,
		READY,
		IN_PROGRESS,
		AWAITING_NEXT_STEPS,
		AWAITING_TEST_RESULTS,
		RE_ENTRY,
		COMPLETED,
		RESCHEDULED,
		CANCELLED
	}

	public enum AssessmentType {
		EXAMINATION,
		TEST
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "assessment_step_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	private Session session;

	@Column(name = "order_in_queue", nullable = false)
	private int orderInQueue;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	@Column(name = "examination_type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private AssessmentType assessmentType;

	@Column(name = "actual_start_time")
	private LocalDateTime actualStartTime;

	@Column(name = "actual_end_time")
	private LocalDateTime actualEndTime;

	@Column(columnDefinition = "TEXT")
	private String notes;

	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;


	@OneToMany(mappedBy = "assessmentStep", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FeedbackDoctor> feedbackDoctors = new ArrayList<>();

	@OneToMany(mappedBy = "assessmentStep", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssessmentStepAttachment> assessmentStepAttachments = new ArrayList<>();
}
