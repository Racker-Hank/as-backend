package vnu.uet.AppointmentScheduler.model.hospital;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column(columnDefinition = "TEXT")
	private String address;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String phone;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Department> departments = new ArrayList<>();
}
