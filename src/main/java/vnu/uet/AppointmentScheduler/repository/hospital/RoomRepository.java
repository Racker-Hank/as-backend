package vnu.uet.AppointmentScheduler.repository.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vnu.uet.AppointmentScheduler.model.hospital.Room;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
//	Page<Room> findAll(Pageable pageable);

	List<Room> findAllByDepartmentId(UUID departmentId);

	Optional<Room> findByIdAndDepartmentId(UUID id, UUID departmentId);
}
