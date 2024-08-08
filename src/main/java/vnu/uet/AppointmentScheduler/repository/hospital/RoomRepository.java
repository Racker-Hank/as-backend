package vnu.uet.AppointmentScheduler.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.model.hospital.Room;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
	@Query("SELECT r FROM Room r WHERE r.department.id = ?1")
	List<Room> findAllByDepartmentId(UUID departmentId);

	@Query("SELECT d FROM Room d WHERE d.department.id = ?1 AND d.id = ?2")
	Optional<Room> findById(UUID departmentId, UUID id);
}
