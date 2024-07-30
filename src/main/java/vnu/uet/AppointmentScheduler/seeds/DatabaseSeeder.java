package vnu.uet.AppointmentScheduler.seeds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.dto.request.RegisterHospitalAdminRequestDTO;
import vnu.uet.AppointmentScheduler.middleware.auth.AuthService;
import vnu.uet.AppointmentScheduler.model.user.HospitalAdmin;

@Slf4j
@Configuration
public class DatabaseSeeder {
	@Bean
	public CommandLineRunner commandLineRunner(
		@Value("${DDL_AUTO}") String ddlAuto,
		@Autowired Environment environment,
		@Autowired AuthService authService
	) {
		return args -> {
			if (!"create-drop".equals(ddlAuto)) return;

			createAdminAccount(environment, authService);
		};
	}

	private void createAdminAccount(Environment environment, AuthService authService) {
		RegisterHospitalAdminRequestDTO registerDTO = RegisterHospitalAdminRequestDTO.builder()
			.email(environment.getProperty("HOSPITAL_ADMIN_EMAIL"))
			.password(environment.getProperty("HOSPITAL_ADMIN_PASSWORD"))
			.firstName(environment.getProperty("HOSPITAL_ADMIN_FIRST_NAME"))
			.lastName(environment.getProperty("HOSPITAL_ADMIN_LAST_NAME"))
			.phone(environment.getProperty("HOSPITAL_ADMIN_PHONE"))
			.build();

		HospitalAdmin hospitalAdmin = (HospitalAdmin) authService.register(UserRole.HOSPITAL_ADMIN, registerDTO);
		log.info("Created a new admin account!");
		log.info(hospitalAdmin.toString());
	}
}
