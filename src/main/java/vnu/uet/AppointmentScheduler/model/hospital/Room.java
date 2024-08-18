package vnu.uet.AppointmentScheduler.model.hospital;

import jakarta.persistence.*;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.RoomType;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(columnDefinition = "NVARCHAR(100)", nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TEXT")
	private String address;

	@Column(name = "room_type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private RoomType roomType;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	//	public enum RoomType {
	//		SPECIALISED,
	//		TESTING
	//	}
}
