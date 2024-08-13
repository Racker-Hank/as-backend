package vnu.uet.AppointmentScheduler.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vnu.uet.AppointmentScheduler.constants.DoctorType;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;

import java.util.UUID;

@Getter
@Setter
public class RegisterDoctorRequestDTO extends RegisterRequestDTO {
	@NotBlank
	private DoctorType doctorType;

	private DepartmentDTO department;
	private UUID departmentId;
}
