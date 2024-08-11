package vnu.uet.AppointmentScheduler.seeds;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.auth.RegisterHospitalAdminRequestDTO;
import vnu.uet.AppointmentScheduler.dto.hospital.HospitalDTO;
import vnu.uet.AppointmentScheduler.middleware.auth.AuthService;
import vnu.uet.AppointmentScheduler.model.hospital.Hospital;
import vnu.uet.AppointmentScheduler.model.user.HospitalAdmin;
import vnu.uet.AppointmentScheduler.service.hospital.HospitalService;

@Slf4j
@Configuration
public class DatabaseSeeder {
	@Bean
	public CommandLineRunner commandLineRunner(
		@Value("${DDL_AUTO}") String ddlAuto,
		@Autowired Environment environment,
		@Autowired AuthService authService,
		@Autowired HospitalService hospitalService
	) {
		return args -> {
			//			if (!"create-drop".equals(ddlAuto) && !"create".equals(ddlAuto)) return;
			//			if (!"create-drop".equals(ddlAuto)) return;
			//			if (!("update".equals(ddlAuto) || "create-drop".equals(ddlAuto)))
			if (!("update".equals(ddlAuto)))
				return;

			Dotenv dotenv = Dotenv.configure().load();

			Hospital hospital = createHospital(dotenv, environment, hospitalService);
			createAdminAccount(dotenv, environment, authService, hospital);
		};
	}

	private Hospital createHospital(
		Dotenv dotenv,
		Environment environment,
		HospitalService hospitalService
	) {
		HospitalDTO hospitalDTO = HospitalDTO.builder()
			.name(dotenv.get("HOSPITAL_NAME"))
			.description(dotenv.get("HOSPITAL_DESCRIPTION"))
			.address(dotenv.get("HOSPITAL_ADDRESS"))
			.phone(dotenv.get("HOSPITAL_PHONE"))
			.build();

		Hospital hospital = hospitalService.save(hospitalDTO);
		log.info("Created a new hospital!");
		log.info(hospital.toString());

		return hospital;
	}

	private void createAdminAccount(
		Dotenv dotenv,
		Environment environment,
		AuthService authService,
		Hospital hospital
	) {
		RegisterHospitalAdminRequestDTO registerDTO = RegisterHospitalAdminRequestDTO.builder()
			.email(dotenv.get("HOSPITAL_ADMIN_EMAIL"))
			.password(dotenv.get("HOSPITAL_ADMIN_PASSWORD"))
			.firstName(dotenv.get("HOSPITAL_ADMIN_FIRST_NAME"))
			.lastName(dotenv.get("HOSPITAL_ADMIN_LAST_NAME"))
			.phone(dotenv.get("HOSPITAL_ADMIN_PHONE"))
			.hospital(hospital)
			.isActive(true)
			.userRole(UserRole.HOSPITAL_ADMIN)
			.build();

		HospitalAdmin hospitalAdmin = (HospitalAdmin) authService.register(UserRole.HOSPITAL_ADMIN, registerDTO);
		log.info("Created a new admin account!");
		log.info(hospitalAdmin.toString());
	}
}
