package vnu.uet.AppointmentScheduler.service.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;

import java.util.List;
import java.util.UUID;

@Service
public interface DepartmentService {
	List<Department> getAllByHospitalId(UUID hospitalId);

	Page<Department> getSomeByHospitalIdWithPagination(UUID hospitalId, Pageable pageable);

	Department save(Department department);

	Department save(UUID hospitalId, DepartmentDTO departmentDTO);

	Department getOneById(UUID id);

	Department updateOne(UUID departmentId, DepartmentDTO department);

	void deleteOne(UUID departmentId);
}
