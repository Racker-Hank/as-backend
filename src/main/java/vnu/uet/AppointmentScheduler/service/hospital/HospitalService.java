package vnu.uet.AppointmentScheduler.service.hospital;

import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.dto.hospital.HospitalDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;

import java.util.UUID;

@Service
public interface HospitalService {
	Hospital save(Hospital hospital);

	Hospital save(HospitalDTO hospitalDTO);

	Hospital getHospitalById(UUID hospitalId);

	Hospital updateOne(UUID hospitalId, HospitalDTO hospital);

}
