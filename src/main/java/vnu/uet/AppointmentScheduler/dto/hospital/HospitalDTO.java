package vnu.uet.AppointmentScheduler.dto.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalDTO {
	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private String address;

	@NotBlank
	private String phone;

	public static HospitalDTO convertToHospitalDTO(Hospital hospital) {
		return HospitalDTO.builder()
			.id(hospital.getId())
			.name(hospital.getName())
			.description(hospital.getDescription())
			.address(hospital.getAddress())
			.phone(hospital.getPhone())
			.build();
	}
}
