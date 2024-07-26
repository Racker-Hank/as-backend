package vnu.uet.AppointmentScheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vnu.uet.AppointmentScheduler.constants.DoctorType;

@Getter
@Setter
public class RegisterDoctorRequestDTO extends RegisterRequestDTO {
	@NotBlank
	private DoctorType doctorType;
}
