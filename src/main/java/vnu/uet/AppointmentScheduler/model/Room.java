package vnu.uet.AppointmentScheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

	public enum RoomType {
		SPECIALISED,
		TESTING
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "room_id")
	private UUID id;

	@Column(columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "room_type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private RoomType roomType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;
}
