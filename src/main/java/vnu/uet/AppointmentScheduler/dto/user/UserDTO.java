package vnu.uet.AppointmentScheduler.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.constants.Gender;
import vnu.uet.AppointmentScheduler.constants.UserRole;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class UserDTO {
	private UUID id;

	@NotBlank
	private String email;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;

	@NotBlank
	private String phone;

	@NotBlank
	private UserRole userRole;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@JsonProperty("isActive")
	private boolean isActive;

	//	private Long createdAt;
	//
	//	private Long updatedAt;

	private Gender gender;

	private Date dob;

	private String address;

	private String avatarUrl;
}
