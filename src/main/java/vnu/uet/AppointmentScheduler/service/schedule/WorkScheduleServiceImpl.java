package vnu.uet.AppointmentScheduler.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.dto.schedule.WorkScheduleDTO;
import vnu.uet.AppointmentScheduler.model.schedule.WorkSchedule;
import vnu.uet.AppointmentScheduler.model.user.Doctor;
import vnu.uet.AppointmentScheduler.repository.schedule.WorkScheduleRepository;
import vnu.uet.AppointmentScheduler.service.user.DoctorService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkScheduleServiceImpl implements WorkScheduleService {
	private final WorkScheduleRepository scheduleRepository;
	private final DoctorService doctorService;

	@Override
	public WorkSchedule save(WorkSchedule schedule) {
		return scheduleRepository.save(schedule);
	}

	@Override
	public WorkSchedule save(UUID doctorId, WorkScheduleDTO scheduleDTO) {
		try {
			Doctor doctor = doctorService.getOneById(scheduleDTO.getDoctorId());

			WorkSchedule schedule = WorkSchedule.builder()
				.doctor(doctor)
				.workScheduleType(scheduleDTO.getWorkScheduleType())
				.createdAt(System.currentTimeMillis())
				.build();

			// TODO: save sessions

			return save(schedule);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public List<WorkSchedule> getAll() {
		return scheduleRepository.findAll();
	}

	@Override
	public List<WorkSchedule> getAllByDoctorId(UUID doctorId) {
		return scheduleRepository.findAllByDoctorId(doctorId);
	}

	@Override
	public WorkSchedule getOneById(UUID doctorId, UUID scheduleId) {
		return scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkSchedule not found"));
	}

	@Override
	public WorkSchedule updateOne(UUID doctorId, UUID scheduleId, WorkScheduleDTO newSchedule) {
		try {
			Doctor doctor = doctorService.getOneById(newSchedule.getDoctorId());

			WorkSchedule schedule = getOneById(doctorId, scheduleId);

			schedule.setWorkScheduleType(newSchedule.getWorkScheduleType());
			schedule.setDoctor(doctor);
			schedule.setUpdatedAt(System.currentTimeMillis());

			// TODO: update sessions

			return save(schedule);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID doctorId, UUID scheduleId) {
		try {
			WorkSchedule workSchedule = getOneById(doctorId, scheduleId);

			scheduleRepository.delete(workSchedule);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
