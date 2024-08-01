package vnu.uet.AppointmentScheduler.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

import java.util.UUID;

public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
}
