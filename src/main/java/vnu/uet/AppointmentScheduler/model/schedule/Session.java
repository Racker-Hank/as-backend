package vnu.uet.AppointmentScheduler.model.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "work_schedule_id")
	private WorkSchedule workSchedule;

	@OneToOne
	@JoinColumn(name = "room_id")
	private Room room;

	@Column(name = "day_of_week", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek dayOfWeek;

	@Column(name = "start_time", nullable = false)
	@JsonFormat(pattern = "HH:mm:ss")
	//	private Long startTime;
	private LocalTime startTime;

	@Column(name = "end_time", nullable = false)
	@JsonFormat(pattern = "HH:mm:ss")
	//	private Long endTime;
	private LocalTime endTime;

	//	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
	//	private List<Appointment> appointments = new ArrayList<>();

	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssessmentStep> assessmentSteps = new ArrayList<>();
}
