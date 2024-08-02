package vnu.uet.AppointmentScheduler.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.constants.UserRole;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String phone;

	@NotBlank
	private UserRole userRole;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private boolean isActive = true;

	private Long createdAt;
}
