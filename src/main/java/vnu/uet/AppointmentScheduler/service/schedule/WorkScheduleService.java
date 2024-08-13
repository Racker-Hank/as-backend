package vnu.uet.AppointmentScheduler.service.schedule;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.schedule.WorkScheduleDTO;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;

import java.util.List;
import java.util.UUID;

@Service
public interface WorkScheduleService {
	WorkSchedule save(WorkSchedule schedule);

	WorkSchedule save(UUID doctorId, WorkScheduleDTO scheduleDTO);

	List<WorkSchedule> getAll();

	List<WorkSchedule> getAllByDoctorId(UUID doctorId);

	WorkSchedule getOneById(UUID doctorId, UUID scheduleId);

	WorkSchedule updateOne(UUID doctorId, UUID scheduleId, WorkScheduleDTO newSchedule);

	void deleteOne(UUID doctorId, UUID scheduleId);

}
