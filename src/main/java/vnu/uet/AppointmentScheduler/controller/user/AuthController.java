package vnu.uet.AppointmentScheduler.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.LoginRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.middleware.auth.AuthService;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	public static final String ACCESS_TOKEN_COOKIE_KEY = "access_token";
	private final AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(
		@Value("${CONTEXT_PATH}") String contextPath,
		@Value("${JWT_EXPIRATION}") Long jwtExpiration,
		@RequestBody LoginRequestDTO loginRequestDTO,
		HttpServletResponse response
	) {
		String jwtToken = authService.login(
			loginRequestDTO.getUserRole(),
			loginRequestDTO.getEmail(),
			loginRequestDTO.getPassword());

		ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_KEY, jwtToken)
			.httpOnly(true)
			.secure(false)
			.path(contextPath)
			.maxAge(jwtExpiration)
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

		return ResponseEntity.ok(jwtToken);
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
					response.addCookie(cookie);
					return ResponseEntity.ok("Cookie cleared");
				}
			}
		}

		return new ResponseEntity<>("Auth token not found", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/me")
	public ResponseEntity<?> authenticateMe() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
		}

		User userDetails = (User) authentication.getPrincipal();
		log.debug(userDetails.toString());

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("id", userDetails.getId());
		responseBody.put("email", userDetails.getEmail());
		responseBody.put("userRole", userDetails.getUserRole());

		return ResponseEntity.ok(responseBody);
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
