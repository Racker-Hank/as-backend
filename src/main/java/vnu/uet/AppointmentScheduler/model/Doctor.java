package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends User {

	public enum DoctorType {
//		EXAMINER,
		SPECIALIST,
		TESTER
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	@Column(name = "doctor_type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private DoctorType doctorType;

	@Column(columnDefinition = "VARCHAR(100)")
	private String degree;

	@Column(columnDefinition = "TEXT")
	private String experience;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkSchedule> workSchedules = new ArrayList<>();
}
