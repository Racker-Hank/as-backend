package vnu.uet.AppointmentScheduler.controller.hospital;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.hospital.DepartmentDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Department;
import vnu.uet.AppointmentScheduler.service.hospital.DepartmentService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<?> getAllDepartments(
		@RequestParam("hospital_id") UUID hospitalId,
		@RequestParam(required = false, defaultValue = "0") Integer page,
		@RequestParam(required = false, defaultValue = "0") Integer size
	) {
		List<DepartmentDTO> departmentDTOs;
		int totalElements;
		int totalPages;

		if (page == 0 && size == 0) {
			List<Department> departments = departmentService.getAllByHospitalId(hospitalId);
			departmentDTOs = departments.stream()
				.map(DepartmentDTO::convertToDepartmentDTO)
				.toList();

			totalElements = departments.size();
			totalPages = 1;
		} else {
			Pageable pageable = PageRequest.of(page - 1, size);
			Page<Department> departmentsPage = departmentService.getSomeByHospitalIdWithPagination(hospitalId, pageable);

			departmentDTOs = departmentsPage.getContent().stream()
				.map(DepartmentDTO::convertToDepartmentDTO)
				.toList();

			totalElements = Math.toIntExact(departmentsPage.getTotalElements());
			totalPages = departmentsPage.getTotalPages();
		}

		Map<String, Object> responseDTO = Map.of(
			"data", departmentDTOs,
			"meta", Map.of(
				"currentPage", page,
				"totalEntries", totalElements,
				"totalPages", totalPages
			)
		);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("fetch-names")
	public ResponseEntity<List<Object>> getAllDepartmentNames(
		@RequestParam("hospital_id") UUID hospitalId
	) {
		record DepartmentName(
			UUID id,
			String name
		) {
		}

		List<Department> departments = departmentService.getAllByHospitalId(hospitalId);
		List<Object> departmentDTOs = departments.stream()
			.map(department -> (Object) new DepartmentName(
				department.getId(),
				department.getName()
			))
			.toList();

		return ResponseEntity.ok(departmentDTOs);
	}

	@GetMapping("{id}")
	public ResponseEntity<DepartmentDTO> getDepartmentById(
		@PathVariable UUID id
	) {
		Department department = departmentService.getOneById(id);

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
		@PathVariable UUID id,
		@RequestBody DepartmentDTO departmentDTO
	) {
		Department department = departmentService.updateOne(id, departmentDTO);

		return ResponseEntity.ok(DepartmentDTO.convertToDepartmentDTO(department));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('" + UserRoleValues.HOSPITAL_ADMIN + "')")
	public ResponseEntity<String> deleteDepartment(
		@PathVariable UUID id
	) {
		departmentService.deleteOne(id);

		return ResponseEntity.ok("Successfully deleted department " + id.toString());
	}
}
