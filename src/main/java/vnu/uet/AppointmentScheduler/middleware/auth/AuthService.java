package vnu.uet.AppointmentScheduler.middleware.auth;

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
import vnu.uet.AppointmentScheduler.dto.request.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterHospitalAdminRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.dto.request.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;
import vnu.uet.AppointmentScheduler.service.DoctorServiceImpl;
import vnu.uet.AppointmentScheduler.service.HospitalAdminServiceImpl;
import vnu.uet.AppointmentScheduler.service.PatientServiceImpl;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	private final UserRepository userRepository;
	private final HospitalAdminServiceImpl hospitalAdminService;
	private final DoctorServiceImpl doctorService;
	private final PatientServiceImpl patientService;

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

	public User register(UserRole userRole, RegisterRequestDTO registerDTO) {
		userRepository.findByEmail(registerDTO.getEmail())
			.ifPresent(user -> {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already registered");
			});

		String hashedPassword = bcryptPasswordEncoder.encode(registerDTO.getPassword());
		registerDTO.setPassword(hashedPassword);

		return switch (userRole) {
			case UserRole.HOSPITAL_ADMIN -> hospitalAdminService.save((RegisterHospitalAdminRequestDTO) registerDTO);
			case UserRole.DOCTOR -> doctorService.save((RegisterDoctorRequestDTO) registerDTO);
			case UserRole.PATIENT -> patientService.save((RegisterPatientRequestDTO) registerDTO);
		};
	}
}
