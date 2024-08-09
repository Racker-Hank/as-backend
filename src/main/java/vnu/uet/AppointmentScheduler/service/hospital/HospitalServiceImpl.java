package vnu.uet.AppointmentScheduler.service.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vnu.uet.AppointmentScheduler.dto.hospital.HospitalDTO;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;
import vnu.uet.AppointmentScheduler.repository.hospital.HospitalRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
	private final HospitalRepository hospitalRepository;

	@Override
	public Hospital save(Hospital hospital) {
		return hospitalRepository.save(hospital);
	}

	@Override
	public Hospital save(HospitalDTO hospitalDTO) {
		try {
			Hospital hospital = Hospital.builder()
				.name(hospitalDTO.getName())
				.description(hospitalDTO.getDescription())
				.address(hospitalDTO.getAddress())
				.phone(hospitalDTO.getPhone())
				.build();

			return save(hospital);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public List<Hospital> getAll() {
		return hospitalRepository.findAll();
	}

	@Override
	public Hospital getOneById(UUID hospitalId) {
		return hospitalRepository.findById(hospitalId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital not " +
				"found"));
	}

	@Override
	public Hospital updateOne(UUID hospitalId, HospitalDTO newHospital) {
		try {
			Hospital hospital = getOneById(hospitalId);

			hospital.setName(newHospital.getName());
			hospital.setDescription(newHospital.getDescription());
			hospital.setPhone(newHospital.getPhone());
			hospital.setAddress(newHospital.getAddress());

			return hospitalRepository.save(hospital);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
