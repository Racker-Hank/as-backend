package vnu.uet.AppointmentScheduler.model.user;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
public class Patient extends User {
}
