package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterDoctorRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.repository.user.DoctorRepository;

@Service
@RequiredArgsConstructor
public class DoctorService {
	private final DoctorRepository doctorRepository;

    public void register(RegisterDoctorRequestDTO registerDTO) {
        Doctor user = Doctor.builder()
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .userRole(UserRole.DOCTOR)
                .doctorType(registerDTO.getDoctorType())
                .createdAt(System.currentTimeMillis())
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .isActive(registerDTO.isActive())
                .build();
        doctorRepository.save(user);
    }
}
