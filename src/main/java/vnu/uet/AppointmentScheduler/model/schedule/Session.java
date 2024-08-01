package vnu.uet.AppointmentScheduler.model.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.time.DayOfWeek;
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

	@Column(name = "start_time", updatable = false, nullable = false)
	private Long startTime;

	@Column(name = "end_time", nullable = false)
	private Long endTime;
}
