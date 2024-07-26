package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.repository.user.DoctorRepository;

@Service
@RequiredArgsConstructor
public class DoctorService {
	private final DoctorRepository doctorRepository;

	public void register(String email, String hashedPassword) {
		Doctor user = Doctor.builder()
			.email(email)
			.password(hashedPassword)
			.userRole(UserRole.DOCTOR)
			.build();
		doctorRepository.save(user);
	}
}
