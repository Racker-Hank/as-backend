package vnu.uet.AppointmentScheduler.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vnu.uet.AppointmentScheduler.constants.Gender;
import vnu.uet.AppointmentScheduler.constants.UserRole;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
	@Email
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(columnDefinition = "VARCHAR(10)", nullable = false)
	@Size(min = 10, max = 10)
	private String phone;

	@Column(name = "user_role", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private UserRole userRole;

	@Column(name = "first_name", columnDefinition = "NVARCHAR(50)", nullable = false)
	private String firstName;

	@Column(name = "last_name", columnDefinition = "NVARCHAR(50)", nullable = false)
	private String lastName;

	@Column(name = "is_active", nullable = false)
	@JsonProperty("isActive")
	private boolean isActive;

	@Column(name = "created_at", updatable = false, nullable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String address;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(userRole.name()));
	}

	// get email
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
}
