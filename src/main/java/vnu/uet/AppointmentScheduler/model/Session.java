package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "session_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_schedule_id")
	private WorkSchedule workSchedule;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

//	@Column(name = "is_active")
//	private boolean isActive;

	@Column(name = "day_of_week", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek dayOfWeek;

	@Column(name = "start_time", updatable = false, nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;
}
