package vnu.uet.AppointmentScheduler.service.hospital;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;

import java.util.List;
import java.util.UUID;

@Service
public interface DepartmentService {
	List<Department> getAll(UUID hospitalId);

	Department save(Department department);

	Department save(UUID hospitalId, DepartmentDTO departmentDTO);

	Department getDepartmentById(UUID hospitalId, UUID id);

	Department updateOne(UUID hospitalId, UUID departmentId, DepartmentDTO department);

	void deleteOne(UUID hospitalId, UUID departmentId);
}
