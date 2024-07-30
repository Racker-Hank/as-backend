package vnu.uet.AppointmentScheduler.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.service.BaseUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final BaseUserService baseUserService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = baseUserService.getAll();

		return ResponseEntity.ok(users);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable(name = "id") UUID userId) {
		User user = baseUserService.getUserById(userId);

		return ResponseEntity.ok(user);
	}
}
