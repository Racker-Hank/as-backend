package vnu.uet.AppointmentScheduler.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private UserRole userRole;
}
