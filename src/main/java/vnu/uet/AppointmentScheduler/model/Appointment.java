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
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "appointment_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id")
	private Session session;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Transient
	private int order;

	@Transient
	private AssessmentStep.Status status;

//	@Transient
//	private

//	@Column(name = "doctor_role")
//	@Enumerated(EnumType.ORDINAL)
//	private DoctorType doctorRole;

//	@Column(columnDefinition = "VARCHAR(100)")
//	private String degree;

	@Column(name = "actual_start_time")
	private long actualStartTime;

	@Column(name = "actual_end_time")
	private long actualEndTime;

	@Transient
	private long estimatedStartTime;

//	@Column(columnDefinition = "TEXT", nullable = false)
//	private String experience;

	@Column(name = "created_at", updatable = false, nullable = false)
	private long createdAt;

	@Column(name = "updated_at")
	private long updatedAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_id")
	private Appointment followupAppointment;

	@Column(name = "followup_appointment_interval")
	private long followupAppointmentInterval;


	@OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FeedbackHospital> feedbackHospitals = new ArrayList<>();

	@OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssessmentStep> assessmentSteps = new ArrayList<>();
}
