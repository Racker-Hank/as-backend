package vnu.uet.AppointmentScheduler.dto.hospital;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	@JsonProperty("description")
	private String services;

	private String email;

	private String phone;
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
			.email(department.getEmail())
			.phone(department.getPhone())
			.hospitalId(hospital.getId())
			.build();
	}
}
