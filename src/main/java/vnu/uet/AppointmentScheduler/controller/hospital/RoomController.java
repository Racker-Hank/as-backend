package vnu.uet.AppointmentScheduler.controller.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.RoomDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.service.hospital.RoomService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;

	@GetMapping
	public ResponseEntity<?> getAllRooms(
		@RequestParam(required = false, defaultValue = "0") Integer page,
		@RequestParam(required = false, defaultValue = "0") Integer size
	) {
		List<RoomDTO> roomDTOs;
		int totalElements;
		int totalPages;

		if (page == 0 && size == 0) {
			List<Room> rooms = roomService.getAll();
			roomDTOs = rooms.stream()
				.map(RoomDTO::convertToRoomDTO)
				.toList();

			totalElements = rooms.size();
			totalPages = 1;
		} else {
			Pageable pageable = PageRequest.of(page - 1, size);
			Page<Room> roomsPage = roomService.getSomeWithPagination(pageable);

			roomDTOs = roomsPage.getContent().stream()
				.map(RoomDTO::convertToRoomDTO)
				.toList();

			totalElements = Math.toIntExact(roomsPage.getTotalElements());
			totalPages = roomsPage.getTotalPages();
		}

		Map<String, Object> responseDTO = Map.of(
			"data", roomDTOs,
			"meta", Map.of(
				"currentPage", page,
				"totalEntries", totalElements,
				"totalPages", totalPages
			)
		);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/by-department")
	public ResponseEntity<List<RoomDTO>> getAllRoomsByDepartment(
		@RequestParam("department_id") UUID departmentId
	) {
		List<Room> rooms = roomService.getAllByDepartmentId(departmentId);
		List<RoomDTO> roomDTOS = rooms
			.stream()
			.map(RoomDTO::convertToRoomDTO)
			.toList();

		return ResponseEntity.ok(roomDTOS);
	}

	@GetMapping("{id}")
	public ResponseEntity<RoomDTO> getRoomById(
		@PathVariable UUID id,
		@RequestParam("department_id") UUID departmentId
	) {
		Room room = roomService.getOneById(departmentId, id);

		return ResponseEntity.ok(RoomDTO.convertToRoomDTO(room));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<RoomDTO> createRoom(
		@RequestParam(
			value = "hospital_id", required = false
		) UUID hospitalId,
		@RequestParam("department_id") UUID departmentId,
		@RequestBody RoomDTO roomDTO
	) {
		Room room = roomService.save(hospitalId, departmentId, roomDTO);

		return new ResponseEntity<>(RoomDTO.convertToRoomDTO(room), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<RoomDTO> updateRoom(
		@RequestParam("hospital_id") UUID hospitalId,
		@RequestParam("department_id") UUID departmentId,
		@PathVariable UUID id,
		@RequestBody RoomDTO roomDTO
	) {
		Room room = roomService.updateOne(
			hospitalId,
			departmentId,
			id,
			roomDTO
		);

		return ResponseEntity.ok(RoomDTO.convertToRoomDTO(room));
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
