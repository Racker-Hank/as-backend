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
@Table(name = "assessment_step_attachment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentStepAttachment  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "assessment_step_attachment_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_step_id")
	private AssessmentStep assessmentStep;

	@Column(columnDefinition = "TEXT", name = "attachment_url")
	private String attachmentUrl;

	@Column(name = "created_at", updatable = false, nullable = false)
	private long createdAt;

	@Column(name = "updated_at")
	private long updatedAt;
}
