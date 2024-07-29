package vnu.uet.AppointmentScheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.service.BaseUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
	private final BaseUserService baseUserService;

	public UserController(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User newUser) {
		User user = baseUserService.saveUser(newUser);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = baseUserService.getAllUsers();

		return ResponseEntity.ok(users);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable(name = "id") UUID userId) {
		User user = baseUserService.loadUserById(userId);

		return ResponseEntity.ok(user);
	}

}
