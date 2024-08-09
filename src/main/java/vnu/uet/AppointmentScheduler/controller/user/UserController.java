package vnu.uet.AppointmentScheduler.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.service.user.BaseUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final BaseUserService<User> baseUserService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = baseUserService.getAll();

		return ResponseEntity.ok(users);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") UUID userId) {
		User user = baseUserService.getOneById(userId);

		return ResponseEntity.ok(UserDTO.convertToUserDTO(user));
	}
}
