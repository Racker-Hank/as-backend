package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "feedback_doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDoctor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "feedback_hospital_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_step_id")
	private AssessmentStep assessmentStep;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@Column(nullable = false)
	private int ratings;

	@Column(columnDefinition = "TEXT")
	private String notes;
}
