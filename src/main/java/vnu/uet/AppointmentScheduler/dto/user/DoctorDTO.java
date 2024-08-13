package vnu.uet.AppointmentScheduler.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.constants.DoctorType;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO extends UserDTO {
	//	@JsonIncludeProperties({"id"})
	private DepartmentDTO departmentDTO;

	private UUID departmentId;

	private DoctorType doctorType;

	private String degree;

	private String experience;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<WorkSchedule> workSchedules;

	public static DoctorDTO convertToDoctorDTO(Doctor doctor) {
		return DoctorDTO.builder()
			.id(doctor.getId())
			.email(doctor.getEmail())
			.phone(doctor.getPhone())
			.userRole(doctor.getUserRole())
			.firstName(doctor.getFirstName())
			.lastName(doctor.getLastName())
			.isActive(doctor.isActive())
			.gender(doctor.getGender())
			.dob(doctor.getDob())
			.address(doctor.getAddress())
			.avatarUrl(doctor.getAvatarUrl())
			//			.department(doctor.getDepartment())
			.departmentId(doctor.getDepartment().getId())
			.doctorType(doctor.getDoctorType())
			.degree(doctor.getDegree())
			.experience(doctor.getExperience())
			.build();
	}
}
