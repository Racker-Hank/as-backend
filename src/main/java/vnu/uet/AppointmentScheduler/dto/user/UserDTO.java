package vnu.uet.AppointmentScheduler.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.constants.Gender;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.time.LocalDate;
import java.util.UUID;

//@Getter
//@Setter
@SuperBuilder
@Data
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	private String address;

	private String avatarUrl;

	public static UserDTO convertToUserDTO(User user) {
		return UserDTO.builder()
			.id(user.getId())
			.email(user.getEmail())
			.phone(user.getPhone())
			.userRole(user.getUserRole())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.isActive(user.isActive())
			.gender(user.getGender())
			.dob(user.getDob())
			.address(user.getAddress())
			.avatarUrl(user.getAvatarUrl())
			.build();
	}
}
