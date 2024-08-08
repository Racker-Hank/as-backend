package vnu.uet.AppointmentScheduler.dto.hospital;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.RoomType;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.model.hospital.Room;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

	private UUID id;

	@NotBlank
	private String name;

	private String description;

	//	@NotBlank
	private RoomType roomType;

	private DepartmentDTO department;

	private UUID departmentId;

	public static RoomDTO convertToRoomDTO(Room room) {
		Department department = room.getDepartment();

		return RoomDTO.builder()
			.id(room.getId())
			.name(room.getName())
			.description(room.getDescription())
			.roomType(room.getRoomType())
			//			.department(DepartmentDTO.convertToDepartmentDTO(department))
			.departmentId(department.getId())
			.build();
	}
}
