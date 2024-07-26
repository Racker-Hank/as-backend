package vnu.uet.AppointmentScheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnu.uet.AppointmentScheduler.exception.AsAuthException;
import vnu.uet.AppointmentScheduler.model.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
