package vnu.uet.AppointmentScheduler.model.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	//	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Column(columnDefinition = "NVARCHAR(100)", nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String services;

	@Column(columnDefinition = "VARCHAR(100)", unique = true)
	@Email
	private String email;

	@Column(columnDefinition = "VARCHAR(10)")
	@Size(min = 10, max = 10)
	private String phone;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Doctor> doctors = new ArrayList<>();

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<>();
}
