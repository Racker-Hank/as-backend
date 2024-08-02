package vnu.uet.AppointmentScheduler.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vnu.uet.AppointmentScheduler.dto.hospital.HospitalDTO;
import vnu.uet.AppointmentScheduler.model.user.HospitalAdmin;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class HospitalAdminDTO extends UserDTO {
	private HospitalDTO hospital;

	private UUID hospitalId;

	public static HospitalAdminDTO convertToHospitalAdminDTO(HospitalAdmin hospitalAdmin) {
		return HospitalAdminDTO.builder()
			.hospital(HospitalDTO.convertToHospitalDTO(hospitalAdmin.getHospital()))
			.hospitalId(hospitalAdmin.getHospital().getId())
			.id(hospitalAdmin.getId())
			.email(hospitalAdmin.getEmail())
			.phone(hospitalAdmin.getPhone())
			.userRole(hospitalAdmin.getUserRole())
			.firstName(hospitalAdmin.getFirstName())
			.lastName(hospitalAdmin.getLastName())
			.isActive(hospitalAdmin.isActive())
			.gender(hospitalAdmin.getGender())
			.dob(hospitalAdmin.getDob())
			.address(hospitalAdmin.getAddress())
			.avatarUrl(hospitalAdmin.getAvatarUrl())
			.build();
	}
}
