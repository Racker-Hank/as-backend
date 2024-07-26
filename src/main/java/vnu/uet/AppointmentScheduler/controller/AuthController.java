package vnu.uet.AppointmentScheduler.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.LoginRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.service.AuthService;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                        HttpServletResponse response) {
        String jwtToken = authService.login(
                loginRequestDTO.getUserRole(),
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
        );

        ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken)
                .httpOnly(true)
                .secure(false)
                .path("/api")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(jwtToken);
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

    @GetMapping("/test")
    public ResponseEntity<String> test(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
