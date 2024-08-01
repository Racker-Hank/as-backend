package vnu.uet.AppointmentScheduler.model.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Hospital {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(columnDefinition = "NVARCHAR(100)", nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String address;

	@Column(columnDefinition = "VARCHAR(10)", nullable = false)
	@Size(min = 10, max = 10)
	private String phone;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Department> departments = new ArrayList<>();
}
