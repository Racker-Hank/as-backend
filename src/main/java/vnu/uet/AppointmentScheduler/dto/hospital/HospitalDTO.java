package vnu.uet.AppointmentScheduler.dto.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

import java.util.UUID;

@Getter
@Setter
@Builder
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
