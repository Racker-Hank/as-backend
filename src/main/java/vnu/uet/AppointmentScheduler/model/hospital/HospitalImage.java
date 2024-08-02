package vnu.uet.AppointmentScheduler.model.hospital;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "hospital_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalImage {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Column(columnDefinition = "TEXT", name = "image_url")
	private String imageUrl;

	@Column(name = "created_at", updatable = false, nullable = false)
	private Long createdAt;

	@Column(name = "updated_at")
	private Long updatedAt;
}
