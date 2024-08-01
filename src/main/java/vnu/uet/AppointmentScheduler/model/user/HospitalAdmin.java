package vnu.uet.AppointmentScheduler.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalAdmin extends User {
	//	@OneToOne
	//	@OneToOne(fetch = FetchType.EAGER)
	@OneToOne
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;
}
