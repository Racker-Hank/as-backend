package vnu.uet.AppointmentScheduler.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.model.hospital.Department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	@Query("SELECT d FROM Department d WHERE d.hospital.id = ?1")
	List<Department> findAll(UUID hospitalId);

	@Query("SELECT d FROM Department d WHERE d.hospital.id = ?1 AND d.id = ?2")
	Optional<Department> findById(UUID hospitalId, UUID id);
}
