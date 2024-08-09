package vnu.uet.AppointmentScheduler.dto.hospital;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO {

	private UUID id;

	@NotBlank
	private String name;

	//	@NotBlank
	private String services;

	//	@NotBlank
	//	private Hospital hospital;
	private HospitalDTO hospital;

	private UUID hospitalId;

	public static DepartmentDTO convertToDepartmentDTO(Department department) {
		Hospital hospital = department.getHospital();

		return DepartmentDTO.builder()
			.id(department.getId())
			.name(department.getName())
			.services(department.getServices())
			//			.hospital(HospitalDTO.convertToHospitalDTO(hospital))
			.hospitalId(hospital.getId())
			.build();
	}
}
