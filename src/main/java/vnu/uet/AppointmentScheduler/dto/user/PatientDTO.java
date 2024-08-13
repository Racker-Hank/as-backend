package vnu.uet.AppointmentScheduler.dto.user;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.model.user.Patient;

//@Data
@SuperBuilder
//@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class PatientDTO extends UserDTO {
	//	@JsonInclude(JsonInclude.Include.NON_NULL)
	//	private String password;

	//	private AppointmentDTO appointment;
	//	private UUID appointmentId;

	public static PatientDTO convertToPatientDTO(Patient patient) {
		//		Appointment appointment = patient.getAppointment();

		return PatientDTO.builder()
			.id(patient.getId())
			.email(patient.getEmail())
			.phone(patient.getPhone())
			.userRole(patient.getUserRole())
			.firstName(patient.getFirstName())
			.lastName(patient.getLastName())
			.isActive(patient.isActive())
			.gender(patient.getGender())
			.dob(patient.getDob())
			.address(patient.getAddress())
			.avatarUrl(patient.getAvatarUrl())
			//			.appointment(AppointmentDTO.convertToDepartmentDTO(appointment))
			//			.appointmentId(appointment.getId())
			.build();
	}
}
