package vnu.uet.AppointmentScheduler.middleware.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterHospitalAdminRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterRequestDTO;
import vnu.uet.AppointmentScheduler.dto.user.DoctorDTO;
import vnu.uet.AppointmentScheduler.dto.user.HospitalAdminDTO;
import vnu.uet.AppointmentScheduler.dto.user.PatientDTO;
import vnu.uet.AppointmentScheduler.dto.user.UserDTO;
import vnu.uet.AppointmentScheduler.model.user.User;
import vnu.uet.AppointmentScheduler.repository.user.UserRepository;
import vnu.uet.AppointmentScheduler.service.user.DoctorServiceImpl;
import vnu.uet.AppointmentScheduler.service.user.HospitalAdminServiceImpl;
import vnu.uet.AppointmentScheduler.service.user.PatientServiceImpl;

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

	public String login(UserRole role, String email, String password) {
		try {
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					email, password,
					List.of(new SimpleGrantedAuthority(role.toString()))
				)
			);
			UserRole actualUserRole = UserRole.valueOf(authentication.getAuthorities().toArray()[0].toString());

			if (role != actualUserRole)
				throw new BadCredentialsException("");
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

	public User register(UserRole role, RegisterRequestDTO registerDTO) {
		userRepository.findByEmail(registerDTO.getEmail())
			.ifPresent(user -> {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already registered");
				}
			);

		String hashedPassword = bcryptPasswordEncoder.encode(registerDTO.getPassword());
		registerDTO.setPassword(hashedPassword);
		registerDTO.setActive(true);

		return switch (role) {
			case UserRole.ADMIN ->
				hospitalAdminService.save((RegisterHospitalAdminRequestDTO) registerDTO);
			case UserRole.DOCTOR -> doctorService.save((RegisterDoctorRequestDTO) registerDTO);
			case UserRole.PATIENT -> patientService.save((RegisterPatientRequestDTO) registerDTO);
		};
	}

	public UserDTO getUserInfo(User user) {
		return switch (user.getRole()) {
			case UserRole.ADMIN -> HospitalAdminDTO.convertToHospitalAdminDTO(
				hospitalAdminService.getOneById(user.getId())
			);
			case UserRole.DOCTOR -> DoctorDTO.convertToDoctorDTO(
				doctorService.getOneById(user.getId())
			);
			case UserRole.PATIENT -> PatientDTO.convertToPatientDTO(
				patientService.getOneById(user.getId())
			);
			//			default ->
			//				throw new IllegalArgumentException("Unknown role: " + user.getAuthorities().stream().toList().getFirst().getAuthority());
		};
	}
}
