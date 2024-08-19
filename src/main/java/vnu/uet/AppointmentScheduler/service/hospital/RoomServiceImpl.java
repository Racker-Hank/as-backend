package vnu.uet.AppointmentScheduler.service.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	private final RoomRepository roomRepository;

	private final DepartmentService departmentService;

	@Override
	public List<Room> getAll() {
		return roomRepository.findAll();
	}

	@Override
	public Page<Room> getSomeWithPagination(Pageable pageable) {
		return roomRepository.findAll(pageable);
	}

	@Override
	public List<Room> getAllByDepartmentId(UUID departmentId) {
		return roomRepository.findAllByDepartmentId(departmentId);
	}

	@Override
	public Room save(Room room) {
		try {
			return roomRepository.save(room);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public Room save(UUID hospitalId, UUID departmentId, RoomDTO roomDTO) {
		try {
			Department department = departmentService.getOneById(departmentId);

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
			return roomRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

		return roomRepository.findByIdAndDepartmentId(id, departmentId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
	}

	@Override
	public Room updateOne(UUID hospitalId, UUID departmentId, UUID roomId, RoomDTO newRoom) {
		try {
			Room room = getOneById(departmentId, roomId);

			UUID newDepartmentId = newRoom.getDepartmentId() != null ?
				newRoom.getDepartmentId() : departmentId;

			Department department = departmentService.getOneById(newDepartmentId);

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

			roomRepository.delete(room);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
