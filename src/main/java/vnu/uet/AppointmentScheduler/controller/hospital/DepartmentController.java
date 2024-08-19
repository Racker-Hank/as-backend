package vnu.uet.AppointmentScheduler.controller.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.service.hospital.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments(
		@RequestParam(
			value = "hospital_id"
			, required = false
		) UUID hospitalId
	) {
		List<Department> departments = departmentService.getAll(hospitalId);
		List<DepartmentDTO> departmentDTOs = departments
			.stream()
			.map(DepartmentDTO::convertToDepartmentDTO)
			.toList();

		return ResponseEntity.ok(departmentDTOs);
	}

	@GetMapping("fetch-names")
	public ResponseEntity<List<Object>> getAllDepartmentNames(
		@RequestParam(
			value = "hospital_id"
			, required = false
		) UUID hospitalId
	) {
		record DepartmentName(
			UUID id,
			String name
		) {}

		List<Department> departments = departmentService.getAll(hospitalId);
		List<Object> departmentDTOs = departments
			.stream()
			.map(department -> (Object) new DepartmentName(
				department.getId(),
				department.getName()
			))
			.toList();

		return ResponseEntity.ok(departmentDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<DepartmentDTO> getDepartmentById(
		@PathVariable UUID id,
		@RequestParam("hospital_id") UUID hospitalId
	) {
		Department department = departmentService.getOneById(hospitalId, id);

		return ResponseEntity.ok(DepartmentDTO.convertToDepartmentDTO(department));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<DepartmentDTO> createDepartment(
		@RequestParam("hospital_id") UUID hospitalId,
		@RequestBody DepartmentDTO departmentDTO
	) {
		Department department = departmentService.save(hospitalId, departmentDTO);

		return new ResponseEntity<>(DepartmentDTO.convertToDepartmentDTO(department), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<DepartmentDTO> updateDepartment(
		@RequestParam(
			value = "hospital_id"
			, required = false
		) UUID hospitalId,
		@PathVariable UUID id,
		@RequestBody DepartmentDTO departmentDTO
	) {
		Department department = departmentService.updateOne(hospitalId, id, departmentDTO);

		return ResponseEntity.ok(DepartmentDTO.convertToDepartmentDTO(department));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<String> deleteDepartment(
		@RequestParam("hospital_id") UUID hospitalId,
		@PathVariable UUID id
	) {
		departmentService.deleteOne(hospitalId, id);

		return ResponseEntity.ok("Successfully deleted department " + id.toString());
	}
}
