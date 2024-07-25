package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	public enum Gender {
		MALE,
		FEMALE
	}

	public enum UserRole {
		PATIENT,
		DOCTOR,
		HOSPITAL_ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID id;

	@Column(name = "first_name", columnDefinition = "NVARCHAR(50)", nullable = false)
	private String firstName;

	@Column(name = "last_name", columnDefinition = "NVARCHAR(50)", nullable = false)
	private String lastName;

	@Column(columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(columnDefinition = "VARCHAR(20)", nullable = false)
	private String phone;

	@Column(name = "user_role", nullable = false)
	//	private String role;
	//	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private UserRole userRole;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String address;

	@Column(name = "avatar_url")
	private String avatarUrl;
}
