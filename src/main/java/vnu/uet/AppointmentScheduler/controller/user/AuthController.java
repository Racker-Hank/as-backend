package vnu.uet.AppointmentScheduler.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.constants.UserRoleValues;
import vnu.uet.AppointmentScheduler.dto.auth.LoginRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.middleware.auth.AuthService;
import vnu.uet.AppointmentScheduler.middleware.auth.JwtService;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final JwtService jwtService;

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(
		@RequestBody LoginRequestDTO loginRequestDTO,
		HttpServletResponse response
	) {
		String jwtToken = authService.login(
			loginRequestDTO.getRole(),
			loginRequestDTO.getEmail(),
			loginRequestDTO.getPassword()
		);

		jwtService.saveAccessTokenToCookie(jwtToken, response);

		return ResponseEntity.ok(jwtToken);
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		boolean success = jwtService.clearAccessTokenFromCookie(request, response);
		if (success) {
			return ResponseEntity.ok("Logout successfully");
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/me")
	//	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> authenticateMe(
		@AuthenticationPrincipal User user,
		@RequestParam(value = "full", defaultValue = "false") boolean full
	) {
		if (user == null)
			//			return new ResponseEntity<>("Auth token not found", HttpStatus.OK);
			return ResponseEntity.ok(null);

		if (!full) {
			Map<String, Object> partialUserDTO = new HashMap<>();
			partialUserDTO.put("id", user.getId());
			partialUserDTO.put("email", user.getEmail());
			partialUserDTO.put("role", user.getRole());

			return ResponseEntity.ok(partialUserDTO);
		} else {
			UserDTO userDTO = authService.getUserInfo(user);

			return ResponseEntity.ok(userDTO);
		}
	}

	@PostMapping(value = "/register/doctor")
	@Secured(UserRoleValues.HOSPITAL_ADMIN)
	public ResponseEntity<String> registerDoctor(@RequestBody RegisterDoctorRequestDTO registerRequestDTO) {
		authService.register(UserRole.DOCTOR, registerRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("New doctor registered successfully");
	}

	@PostMapping(value = "/register/patient")
	public ResponseEntity<String> registerPatient(@RequestBody RegisterPatientRequestDTO registerRequestDTO) {
		authService.register(UserRole.PATIENT, registerRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("New patient registered successfully");
	}
}
