package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterPatientRequestDTO;
import vnu.uet.AppointmentScheduler.model.user.Patient;
import vnu.uet.AppointmentScheduler.repository.user.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public void register(RegisterPatientRequestDTO registerRequestDTO) {
        Patient user = Patient.builder()
                .email(registerRequestDTO.getEmail())
                .password(registerRequestDTO.getPassword())
                .userRole(UserRole.PATIENT)
                .createdAt(System.currentTimeMillis())
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .isActive(registerRequestDTO.isActive())
                .build();
        patientRepository.save(user);
    }
}
