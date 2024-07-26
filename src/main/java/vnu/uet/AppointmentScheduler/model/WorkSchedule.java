package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "work_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkSchedule {

	public enum WorkScheduleType {
		DEFAULT,
		TEMPORARY
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "work_schedule_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private WorkScheduleType workScheduleType;

//	@Column(name = "is_active")
//	private boolean isActive;

	@Column(name = "created_at", updatable = false)
	private long createdAt;

	@Column(name = "updated_at")
	private long updatedAt;

	@OneToMany(mappedBy = "workSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Session> sessions = new ArrayList<>();
}
