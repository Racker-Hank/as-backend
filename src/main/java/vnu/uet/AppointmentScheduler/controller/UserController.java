package vnu.uet.AppointmentScheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User newUser) {
		User user = userService.saveUser(newUser);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();

		return ResponseEntity.ok(users);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable(name = "id") UUID userId) {
		User user = userService.getUserById(userId);

		return ResponseEntity.ok(user);
	}

}
