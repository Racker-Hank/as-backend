package vnu.uet.AppointmentScheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.HospitalAdmin;
import vnu.uet.AppointmentScheduler.repository.user.HospitalAdminRepository;

@Service
@RequiredArgsConstructor
public class HospitalAdminService {
    private final HospitalAdminRepository hospitalAdminRepository;

    public void register(String email, String hashedPassword) {
        HospitalAdmin user = HospitalAdmin.builder()
                .email(email)
                .password(hashedPassword)
                .userRole(UserRole.HOSPITAL_ADMIN)
                .build();
        hospitalAdminRepository.save(user);
    }
}
