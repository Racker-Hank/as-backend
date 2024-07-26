package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	private final UserRepository userRepository;
	private final HospitalAdminService hospitalAdminService;
	private final DoctorService doctorService;
	private final PatientService patientService;

	public String login(UserRole userRole, String email, String password) {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					email, password,
					List.of(new SimpleGrantedAuthority(userRole.toString()))
				)
			);
		} catch (Exception exc) {
			if (exc instanceof BadCredentialsException) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials!");
			}
			throw exc;
		}

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		return jwtService.generateToken(user);
	}

	public void register(UserRole userRole, RegisterRequestDTO request) {
		userRepository.findByEmail(request.getEmail())
			.ifPresent(user -> {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already registered");
			});

		String hashedPassword = bcryptPasswordEncoder.encode(request.getPassword());

		switch (userRole) {
			case UserRole.HOSPITAL_ADMIN ->
				hospitalAdminService.register(request.getEmail(), hashedPassword);
			case UserRole.DOCTOR -> doctorService.register(request.getEmail(), hashedPassword);
			case UserRole.PATIENT -> patientService.register(request.getEmail(), hashedPassword);
		}
	}
}
