package vnu.uet.AppointmentScheduler.repository.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnu.uet.AppointmentScheduler.model.hospital.Department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	List<Department> findAllByHospitalId(UUID hospitalId);

	Page<Department> findAllByHospitalId(UUID hospitalId, Pageable pageable);

	Optional<Department> findByIdAndHospitalId(UUID hospitalId, UUID id);
}
