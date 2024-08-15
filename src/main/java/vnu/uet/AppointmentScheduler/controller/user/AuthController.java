package vnu.uet.AppointmentScheduler.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.LoginRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.middleware.auth.AuthService;
import vnu.uet.AppointmentScheduler.model.user.User;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	public static final String ACCESS_TOKEN_COOKIE_KEY = "access_token";
	public static final String ACCESS_TOKEN_COOKIE_PATH = "/api";
	private final AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(
		@RequestBody LoginRequestDTO loginRequestDTO,
		HttpServletResponse response
	) {
		String jwtToken = authService.login(
			loginRequestDTO.getUserRole(),
			loginRequestDTO.getEmail(),
			loginRequestDTO.getPassword()
		);

		ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_KEY, jwtToken)
			.httpOnly(true)
			.secure(false)
			.path(ACCESS_TOKEN_COOKIE_PATH)
			.maxAge(7 * 24 * 60 * 60)
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

		return ResponseEntity.ok(jwtToken);
	}

	@GetMapping("me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserDTO> getUserInfo(
		@AuthenticationPrincipal User user
	) {
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authenticated");
		}

		UserDTO userDTO = authService.getUserInfo(user);

		return ResponseEntity.ok(userDTO);
	}

	@GetMapping("logout")
	public ResponseEntity<String> logout(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ACCESS_TOKEN_COOKIE_KEY.equals(cookie.getName())) {
					cookie.setValue(null); // Clear the value
					cookie.setMaxAge(0);   // Set cookie to expire immediately
					cookie.setPath(ACCESS_TOKEN_COOKIE_PATH);
					cookie.setHttpOnly(true);
					cookie.setSecure(false);
					response.addCookie(cookie);
					return ResponseEntity.ok("Cookie cleared");
				}
			}
		}

		return new ResponseEntity<>("Auth token not found", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = "/register/doctor")
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
