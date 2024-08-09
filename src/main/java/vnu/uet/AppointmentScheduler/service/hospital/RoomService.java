package vnu.uet.AppointmentScheduler.service.hospital;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.hospital.RoomDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Room;

import java.util.List;
import java.util.UUID;

@Service
public interface RoomService {
	List<Room> getAllByDepartmentId(UUID departmentId);

	Room getOneById(UUID departmentId, UUID id);

	Room save(Room department);

	Room save(UUID hospitalId, UUID departmentId, RoomDTO roomDTO);

	Room updateOne(UUID hospitalId, UUID departmentId, UUID roomId, RoomDTO newRoom);

	void deleteOne(UUID departmentId, UUID roomId);
}
