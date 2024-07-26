package vnu.uet.AppointmentScheduler.model.appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "assessment_step_attachment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentStepAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_step_id")
	private AssessmentStep assessmentStep;

	@Column(columnDefinition = "TEXT", name = "attachment_url")
	private String attachmentUrl;

	@Column(name = "created_at", updatable = false, nullable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;
}
