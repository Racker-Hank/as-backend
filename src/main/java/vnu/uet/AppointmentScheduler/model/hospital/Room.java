package vnu.uet.AppointmentScheduler.model.hospital;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnu.uet.AppointmentScheduler.constants.RoomType;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(columnDefinition = "NVARCHAR(100)", nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

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
