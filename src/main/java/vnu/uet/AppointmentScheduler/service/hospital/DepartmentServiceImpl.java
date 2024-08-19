package vnu.uet.AppointmentScheduler.service.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;
import vnu.uet.AppointmentScheduler.repository.hospital.DepartmentRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	private final HospitalService hospitalService;

	@Override
	public List<Department> getAllByHospitalId(UUID hospitalId) {
		return departmentRepository.findAllByHospitalId(hospitalId);
	}

	@Override
	public Page<Department> getSomeByHospitalIdWithPagination(UUID hospitalId, Pageable pageable) {
		return departmentRepository.findAllByHospitalId(hospitalId, pageable);
	}

	@Override
	public Department save(Department department) {
		try {
			return departmentRepository.save(department);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public Department save(UUID hospitalId, DepartmentDTO departmentDTO) {
		try {
			Hospital hospital = hospitalService.getOneById(hospitalId);
			//		departmentDTO.setHospital(hospital);

			Department department = Department.builder()
				.hospital(hospital)
				.name(departmentDTO.getName())
				.services(departmentDTO.getServices())
				.email(departmentDTO.getEmail())
				.phone(departmentDTO.getPhone())
				.build();

			return save(department);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public Department getOneById(UUID id) {
		return departmentRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
	}

	@Override
	public Department updateOne(UUID departmentId, DepartmentDTO newDepartment) {
		try {
			Department department = getOneById(departmentId);

			department.setName(newDepartment.getName());
			department.setServices(newDepartment.getServices());
			department.setEmail(newDepartment.getEmail());
			department.setPhone(newDepartment.getPhone());

			return save(department);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID departmentId) {
		try {
			Department department = getOneById(departmentId);

			departmentRepository.delete(department);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
