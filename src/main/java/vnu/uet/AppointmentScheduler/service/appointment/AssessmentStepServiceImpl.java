package vnu.uet.AppointmentScheduler.service.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.dto.appointment.AssessmentStepDTO;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;
import vnu.uet.AppointmentScheduler.model.schedule.Session;
import vnu.uet.AppointmentScheduler.repository.appointment.AssessmentStepRepository;
import vnu.uet.AppointmentScheduler.service.schedule.SessionService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
//@RequiredArgsConstructor
public class AssessmentStepServiceImpl implements AssessmentStepService {

	private final AssessmentStepRepository assessmentStepRepository;

	//	@Lazy
	private final AppointmentService appointmentService;
	private final SessionService sessionService;

	@Autowired
	public AssessmentStepServiceImpl(
		AssessmentStepRepository assessmentStepRepository,
		@Lazy AppointmentService appointmentService,
		SessionService sessionService
	) {
		this.assessmentStepRepository = assessmentStepRepository;
		this.appointmentService = appointmentService;
		this.sessionService = sessionService;
	}

	@Override
	public AssessmentStep save(AssessmentStep assessmentStep) {
		try {
			return assessmentStepRepository.save(assessmentStep);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public AssessmentStep save(
		UUID patientId,
		UUID appointmentId,
		UUID sessionId,
		AssessmentStepDTO assessmentStepDTO
	) {
		try {
			Appointment appointment = appointmentService.getOneById(patientId, appointmentId);

			Session session = sessionService.getOneById(null, sessionId);

			List<AssessmentStep> assessmentStepsBySession = getAllBySessionId(sessionId);

			int currentOrder =
				(assessmentStepsBySession == null || assessmentStepsBySession.isEmpty()) ? -1 :
					assessmentStepsBySession.size();

			AssessmentStep assessmentStep = AssessmentStep.builder()
				.appointment(appointment)
				.session(session)
				.orderInQueue(currentOrder + 1)
				.status(AssessmentStepStatus.IN_QUEUE)
				.assessmentType(assessmentStepDTO.getAssessmentType())
				.actualStartTime(assessmentStepDTO.getActualStartTime())
				.actualEndTime(assessmentStepDTO.getActualEndTime())
				.notes(assessmentStepDTO.getNotes())
				.createdAt(System.currentTimeMillis())
				.build();

			return save(assessmentStep);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public List<AssessmentStep> getAll() {
		return assessmentStepRepository.findAll();
	}

	@Override
	public List<AssessmentStep> getAllByAppointmentId(UUID appointmentId) {

		return assessmentStepRepository.findAllByAppointmentId(appointmentId);
	}

	@Override
	public List<AssessmentStep> getAllBySessionId(UUID sessionId) {

		return assessmentStepRepository.findAllBySessionId(sessionId);
	}

	@Override
	public List<AssessmentStep> getAllByDoctorId(UUID doctorId) {

		return assessmentStepRepository.findAllByDoctorId(doctorId);
	}

	@Override
	public List<AssessmentStep> getAllByPatientId(UUID patientId) {
		return assessmentStepRepository.findAllByPatientId(patientId);
	}

	@Override
	public List<AssessmentStep> getAllByStatus(AssessmentStepStatus status) {
		//		return assessmentStepRepository.findAllByStatus(status);
		return assessmentStepRepository.findAllByStatus(status);
	}

	@Override
	public AssessmentStep getOneById(UUID appointmentId, UUID id) {
		AssessmentStep assessmentStep;
		if (appointmentId == null) {
			assessmentStep = assessmentStepRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AssessmentStep not found"));
		} else {
			assessmentStep = assessmentStepRepository.findById(appointmentId, id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AssessmentStep not found"));
		}

		return assessmentStep;
	}

	@Override
	public AssessmentStep updateOne(
		UUID appointmentId,
		UUID sessionId,
		UUID assessmentStepId,
		AssessmentStepDTO newAssessmentStep
	) {
		try {
			AssessmentStep assessmentStep = getOneById(appointmentId, assessmentStepId);

			Appointment appointment = newAssessmentStep.getAppointmentId() != null ?
				appointmentService.getOneById(null, newAssessmentStep.getAppointmentId()) :
				assessmentStep.getAppointment();

			Session session = sessionService.getOneById(null, newAssessmentStep.getSessionId());

			assessmentStep.setAppointment(appointment);
			assessmentStep.setSession(session);
			assessmentStep.setOrderInQueue(newAssessmentStep.getOrderInQueue());
			assessmentStep.setStatus(newAssessmentStep.getStatus());
			assessmentStep.setAssessmentType(newAssessmentStep.getAssessmentType());
			assessmentStep.setActualStartTime(newAssessmentStep.getActualStartTime());
			assessmentStep.setActualEndTime(newAssessmentStep.getActualEndTime());
			assessmentStep.setNotes(newAssessmentStep.getNotes());
			assessmentStep.setUpdatedAt(System.currentTimeMillis());

			assessmentStep = save(assessmentStep);

			return assessmentStep;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public void deleteOne(UUID appointmentId, UUID assessmentStepId) {
		try {
			AssessmentStep assessmentStep = getOneById(appointmentId, assessmentStepId);

			assessmentStepRepository.delete(assessmentStep);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
