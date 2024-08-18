package vnu.uet.AppointmentScheduler.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	//	@NotBlank
	private UUID workScheduleId;

	private RoomDTO room;
	@NotBlank
	private UUID roomId;

	private DayOfWeek dayOfWeek;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime startTime;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime endTime;

	private String shift;

	public static SessionDTO convertToSessionDTO(Session session) {
		Doctor doctor = session.getDoctor();
		WorkSchedule workSchedule = session.getWorkSchedule();
		Room room = session.getRoom();
		String shift = session.getEndTime()
			.isBefore(LocalTime.of(12, 0, 1))
			? "Morning"
			: "Afternoon";

		return SessionDTO.builder()
			.id(workSchedule.getId())
			.doctor(DoctorDTO.convertToDoctorDTO(doctor))
			.doctorId(doctor.getId())
			.workScheduleId(workSchedule.getId())
			.roomId(room.getId())
			.room(RoomDTO.convertToRoomDTO(room))
			.dayOfWeek(session.getDayOfWeek())
			.startTime(session.getStartTime())
			.endTime(session.getEndTime())
			.shift(shift)
			.build();
	}
}
