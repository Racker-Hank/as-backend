package vnu.uet.AppointmentScheduler.dto.hospital;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HospitalDTO {
	//	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	private String address;

	@NotBlank
	private String phone;
}
