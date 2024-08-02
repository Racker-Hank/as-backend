package vnu.uet.AppointmentScheduler.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private UserRole userRole;
}
