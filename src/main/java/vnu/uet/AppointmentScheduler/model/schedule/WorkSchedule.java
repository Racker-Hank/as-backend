package vnu.uet.AppointmentScheduler.model.schedule;

import jakarta.persistence.*;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.WorkScheduleType;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "work_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private WorkScheduleType workScheduleType;

	@Column(name = "created_at", updatable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;

	@OneToMany(mappedBy = "workSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Session> sessions = new ArrayList<>();

	//	public enum WorkScheduleType {
	//		DEFAULT,
	//		TEMPORARY
	//	}
}
