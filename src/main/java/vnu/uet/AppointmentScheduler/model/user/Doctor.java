package vnu.uet.AppointmentScheduler.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.constants.DoctorType;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends User {
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
