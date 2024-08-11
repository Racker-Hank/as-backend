package vnu.uet.AppointmentScheduler.service.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.constants.AssessmentStepStatus;
import vnu.uet.AppointmentScheduler.dto.appointment.AppointmentDTO;
import vnu.uet.AppointmentScheduler.model.appointment.Appointment;
import vnu.uet.AppointmentScheduler.model.appointment.AssessmentStep;
import vnu.uet.AppointmentScheduler.model.user.Patient;
import vnu.uet.AppointmentScheduler.repository.appointment.AppointmentRepository;
import vnu.uet.AppointmentScheduler.service.user.PatientService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepository;

	private final PatientService patientService;

	@Override
	public Appointment save(Appointment appointment) {
		try {
			return appointmentRepository.save(appointment);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public Appointment save(UUID patientId, AppointmentDTO appointmentDTO) {
		try {
			Patient patient = patientService.getOneById(patientId);

			Appointment followupAppointment = appointmentDTO.getFollowupAppointmentId() != null ?
				getOneById(patientId, appointmentDTO.getFollowupAppointmentId()) :
				null;

			Appointment appointment = Appointment.builder()
				.patient(patient)
				.actualStartTime(appointmentDTO.getActualStartTime())
				.actualEndTime(appointmentDTO.getActualEndTime())
				.followupAppointment(followupAppointment)
				.followupAppointmentInterval(appointmentDTO.getFollowupAppointmentInterval())
				.createdAt(System.currentTimeMillis())
				.build();

			return save(appointment);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public List<Appointment> getAll() {
		return appointmentRepository.findAll();
	}

	@Override
	public List<Appointment> getALLByPatientId(UUID patientId) {
		return appointmentRepository.findAllByPatientId(patientId);
	}

	@Override
	public List<Appointment> getAllByStatus(AssessmentStepStatus status) {
		return appointmentRepository.findAllByStatus(status);
	}

	private AssessmentStepStatus getAppointmentStatus(Appointment appointment) {
		List<AssessmentStep> assessmentSteps = appointment.getAssessmentSteps();

		if (assessmentSteps != null && !assessmentSteps.isEmpty()) {
			//			for (AssessmentStep assessmentStep : assessmentSteps) {
			//				AssessmentStepStatus status = assessmentStep.getStatus();
			//				if (status == AssessmentStepStatus.CANCELLED)
			//					continue;
			//
			//				if (status != AssessmentStepStatus.COMPLETED)
			//					return status;
			//			}

			for (int i = 0; i < assessmentSteps.size(); i++) {
				AssessmentStepStatus status = assessmentSteps.get(i).getStatus();
				//				if (status == AssessmentStepStatus.CANCELLED)
				//					continue;

				if (status == AssessmentStepStatus.RESCHEDULED) {
					//					if (i == assessmentSteps.size() - 1) status
					continue;
				}

				if (status != AssessmentStepStatus.COMPLETED)
					return status;
			}

			return AssessmentStepStatus.COMPLETED;
		}

		return null;
	}

	@Override
	public Appointment getOneById(UUID patientId, UUID id) {
		if (patientId == null)
			return appointmentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

		return appointmentRepository.findById(patientId, id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
	}

	@Override
	public Appointment updateOne(
		UUID patientId,
		UUID appointmentId,
		AppointmentDTO newAppointment
	) {
		try {
			Appointment appointment = getOneById(patientId, appointmentId);

			Appointment followupAppointment = newAppointment.getFollowupAppointmentId() != null ?
				getOneById(patientId, newAppointment.getFollowupAppointmentId()) :
				null;

			appointment.setActualStartTime(newAppointment.getActualStartTime());
			appointment.setActualEndTime(newAppointment.getActualEndTime());
			appointment.setFollowupAppointment(followupAppointment);
			appointment.setFollowupAppointmentInterval(newAppointment.getFollowupAppointmentInterval());
			appointment.setUpdatedAt(System.currentTimeMillis());

			appointment = save(appointment);

			//			appointment.setOrder();

			return appointment;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	//	@Override
	public Appointment updateOneStatus(
		UUID patientId,
		UUID appointmentId,
		AssessmentStepStatus newStatus
	) {
		Appointment appointment = getOneById(patientId, appointmentId);

		appointment.setStatus(newStatus);
		appointment.setUpdatedAt(System.currentTimeMillis());

		//		return appointmentRepository.save(appointment);
		return appointment;
	}

	@Override
	public void deleteOne(UUID patientId, UUID appointmentId) {
		try {
			Appointment appointment = getOneById(patientId, appointmentId);

			appointmentRepository.delete(appointment);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
