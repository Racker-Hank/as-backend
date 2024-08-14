package vnu.uet.AppointmentScheduler.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vnu.uet.AppointmentScheduler.dto.hospital.RoomDTO;
import vnu.uet.AppointmentScheduler.dto.user.DoctorDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Room;
import vnu.uet.AppointmentScheduler.model.schedule.Session;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;
import vnu.uet.AppointmentScheduler.model.user.Doctor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDTO {
	private UUID id;

	private DoctorDTO doctor;
	@NotBlank
	private UUID doctorId;

	private WorkScheduleDTO workSchedule;
	@NotBlank
	private UUID workScheduleId;

	private RoomDTO room;
	@NotBlank
	private UUID roomId;

	private DayOfWeek dayOfWeek;

	private LocalTime startTime;

	private LocalTime endTime;

	public static SessionDTO convertToSessionDTO(Session session) {
		Doctor doctor = session.getDoctor();
		WorkSchedule workSchedule = session.getWorkSchedule();
		Room room = session.getRoom();

		return SessionDTO.builder()
			.id(workSchedule.getId())
			.doctorId(doctor.getId())
			.workScheduleId(workSchedule.getId())
			.roomId(room.getId())
			.dayOfWeek(session.getDayOfWeek())
			.startTime(session.getStartTime())
			.endTime(session.getEndTime())
			.build();
	}
}
