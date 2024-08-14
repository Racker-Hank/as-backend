package vnu.uet.AppointmentScheduler.service.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.dto.hospital.RoomDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.repository.hospital.RoomRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomRepository departmentRepository;

	private final DepartmentService departmentService;

	@Override
	public List<Room> getAllByDepartmentId(UUID departmentId) {
		return departmentRepository.findAllByDepartmentId(departmentId);
	}

	@Override
	public Room save(Room room) {
		try {
			return departmentRepository.save(room);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public Room save(UUID hospitalId, UUID departmentId, RoomDTO roomDTO) {
		try {
			Department department = departmentService.getOneById(hospitalId, departmentId);

			Room room = Room.builder()
				.department(department)
				.name(roomDTO.getName())
				.description(roomDTO.getDescription())
				.roomType(roomDTO.getRoomType())
				.department(department)
				.build();

			return save(room);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public Room getOneById(UUID departmentId, UUID id) {
		if (departmentId == null)
			return departmentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

		return departmentRepository.findById(departmentId, id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
	}

	@Override
	public Room updateOne(UUID hospitalId, UUID departmentId, UUID roomId, RoomDTO newRoom) {
		try {
			Room room = getOneById(departmentId, roomId);

			UUID newDepartmentId = newRoom.getDepartmentId() != null ?
				newRoom.getDepartmentId() : departmentId;

			Department department = departmentService.getOneById(hospitalId,
				newDepartmentId);

			room.setName(newRoom.getName());
			room.setDescription(newRoom.getDescription());
			room.setRoomType(newRoom.getRoomType());
			room.setDepartment(department);

			return save(room);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID departmentId, UUID roomId) {
		try {
			Room room = getOneById(departmentId, roomId);

			departmentRepository.delete(room);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
