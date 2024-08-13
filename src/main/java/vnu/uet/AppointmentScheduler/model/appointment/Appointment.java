package vnu.uet.AppointmentScheduler.model.appointment;

import jakarta.persistence.*;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.model.user.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	//	@ManyToOne
	//	@JoinColumn(name = "session_id")
	//	private Session session;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Transient
	private Integer order;

	@Transient
	private AssessmentStepStatus status;

//	@Transient
//	private

//	@Column(name = "doctor_role")
//	@Enumerated(EnumType.ORDINAL)
//	private DoctorType doctorRole;

//	@Column(columnDefinition = "VARCHAR(100)")
//	private String degree;

	@Column(name = "actual_start_time")
	private Long actualStartTime;

	@Column(name = "actual_end_time")
	private Long actualEndTime;

	@Transient
	private Long estimatedStartTime;

//	@Column(columnDefinition = "TEXT", nullable = false)
//	private String experience;

	@Column(name = "created_at", updatable = false, nullable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;

	@OneToOne
	@JoinColumn(name = "followup_appointment_id")
	private Appointment followupAppointment;

	@Column(name = "followup_appointment_interval")
	private Long followupAppointmentInterval;

	@OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FeedbackHospital> feedbackHospitals = new ArrayList<>();

	@OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssessmentStep> assessmentSteps = new ArrayList<>();
}
