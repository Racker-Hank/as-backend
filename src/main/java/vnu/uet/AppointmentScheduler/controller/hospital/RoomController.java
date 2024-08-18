package vnu.uet.AppointmentScheduler.controller.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.RoomDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.service.hospital.RoomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;

	@GetMapping
	public ResponseEntity<List<RoomDTO>> getAllRooms(
		@RequestParam("department_id") UUID departmentId
	) {
		List<Room> departments = roomService.getAllByDepartmentId(departmentId);
		List<RoomDTO> departmentDTOs = departments
			.stream()
			.map(RoomDTO::convertToRoomDTO)
			.toList();

		return ResponseEntity.ok(departmentDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<RoomDTO> getRoomById(
		@PathVariable UUID id,
		@RequestParam("department_id") UUID departmentId
	) {
		Room department = roomService.getOneById(departmentId, id);

		return ResponseEntity.ok(RoomDTO.convertToRoomDTO(department));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<RoomDTO> createRoom(
		@RequestParam(
			value = "hospital_id"
			, required = false
		) UUID hospitalId,
		@RequestParam("department_id") UUID departmentId,
		@RequestBody RoomDTO roomDTO
	) {
		Room department = roomService.save(hospitalId, departmentId, roomDTO);

		return new ResponseEntity<>(RoomDTO.convertToRoomDTO(department), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<RoomDTO> updateRoom(
		@RequestParam("hospital_id") UUID hospitalId,
		@RequestParam("department_id") UUID departmentId,
		@PathVariable UUID id,
		@RequestBody RoomDTO roomDTO
	) {
		Room department = roomService.updateOne(
			hospitalId,
			departmentId,
			id,
			roomDTO
		);

		return ResponseEntity.ok(RoomDTO.convertToRoomDTO(department));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<String> deleteRoom(
		@RequestParam("department_id") UUID departmentId,
		@PathVariable UUID id
	) {
		roomService.deleteOne(departmentId, id);

		return ResponseEntity.ok("Successfully deleted room " + id.toString());
	}
}
