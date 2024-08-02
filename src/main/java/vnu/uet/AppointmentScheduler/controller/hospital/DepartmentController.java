package vnu.uet.AppointmentScheduler.controller.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.service.hospital.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hospital/{hospital_id}/department")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments(
		@PathVariable("hospital_id") UUID hospitalId
	) {
		List<Department> departments = departmentService.fetchAllDepartments(hospitalId);
		List<DepartmentDTO> departmentDTOs = departments
			.stream()
			.map(DepartmentDTO::convertToDepartmentDTO)
			.toList();

		return ResponseEntity.ok(departmentDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<DepartmentDTO> getDepartmentById(
		@PathVariable UUID id,
		@PathVariable("hospital_id") UUID hospitalId
	) {
		Department department = departmentService.getDepartmentById(hospitalId, id);

		return ResponseEntity.ok(DepartmentDTO.convertToDepartmentDTO(department));
	}

	@PostMapping
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<DepartmentDTO> createDepartment(
		@PathVariable("hospital_id") UUID hospitalId,
		@RequestBody DepartmentDTO departmentDTO
	) {
		Department department = departmentService.save(hospitalId, departmentDTO);

		return new ResponseEntity<>(DepartmentDTO.convertToDepartmentDTO(department), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<DepartmentDTO> updateDepartment(
		@PathVariable("hospital_id") UUID hospitalId,
		@PathVariable UUID id,
		@RequestBody DepartmentDTO departmentDTO
	) {
		Department department = departmentService.updateOne(hospitalId, id, departmentDTO);

		return ResponseEntity.ok(DepartmentDTO.convertToDepartmentDTO(department));
	}

	@DeleteMapping("{id}")
	@Secured({ UserRoleValues.HOSPITAL_ADMIN })
	public ResponseEntity<String> deleteDepartment(
		@PathVariable("hospital_id") UUID hospitalId,
		@PathVariable UUID id
	) {
		departmentService.deleteOne(hospitalId, id);

		return ResponseEntity.ok("Successfully deleted department " + id.toString());
	}
}
