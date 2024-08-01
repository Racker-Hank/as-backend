package vnu.uet.AppointmentScheduler.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

@SuperBuilder
@Getter
@Setter
public class RegisterHospitalAdminRequestDTO extends RegisterRequestDTO {
	@NotBlank
	private Hospital hospital;
}
