package vnu.uet.AppointmentScheduler.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.constants.WorkScheduleType;
import vnu.uet.AppointmentScheduler.dto.user.DoctorDTO;
import vnu.uet.AppointmentScheduler.model.schedule.Session;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkScheduleDTO {
	private UUID id;

	//	@NotBlank
	private DoctorDTO doctorDTO;

	//	@NotBlank
	private UUID doctorId;

	private List<Session> sessions;

	@NotBlank
	private WorkScheduleType workScheduleType;

	public static WorkScheduleDTO convertToWorkScheduleDTO(WorkSchedule workSchedule) {
		Doctor doctor = workSchedule.getDoctor();

		return WorkScheduleDTO.builder()
			.id(workSchedule.getId())
			.doctorDTO(DoctorDTO.convertToDoctorDTO(doctor))
			.doctorId(doctor.getId())
			.workScheduleType(workSchedule.getWorkScheduleType())
			//			.sessions(workSchedule.getSessions())
			.build();
	}
}
