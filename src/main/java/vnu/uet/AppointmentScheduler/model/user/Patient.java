package vnu.uet.AppointmentScheduler.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends User {

	//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	//	@JoinColumn(name = "appointment_id")
	//	private Appointment appointment;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Appointment> appointments = new ArrayList<>();
}
