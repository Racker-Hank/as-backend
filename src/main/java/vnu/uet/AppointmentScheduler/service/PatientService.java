package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.Patient;
import vnu.uet.AppointmentScheduler.repository.user.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientService {
	private final PatientRepository patientRepository;

	public void register(String email, String hashedPassword) {
		Patient user = Patient.builder()
			.email(email)
			.password(hashedPassword)
			.userRole(UserRole.PATIENT)
			.build();
		patientRepository.save(user);
	}
}
