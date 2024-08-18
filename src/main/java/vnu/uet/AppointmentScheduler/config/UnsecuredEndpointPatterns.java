package vnu.uet.AppointmentScheduler.config;

import lombok.RequiredArgsConstructor;

/*
	Unsecured URL patterns (without the context path)
 */
@RequiredArgsConstructor
public enum UnsecuredEndpointPatterns {

	//	GET method-supported endpoint patterns
	GET_METHOD(new String[] {
		"/auth/logout",
		"/hospital/**",
		"/department/**",
		"/room/**",
		"/session/**",
		"/work-schedule/**",

	}),

	//	Other method-supported endpoint patterns
	//	POST, PUT, PATCH, DELETE
	NON_GET_METHODS(new String[] {
		"/auth/login",
		"/auth/me",
		"/auth/register/patient",
	});

	private final String[] patterns;

	public String[] retrieve() {
		return patterns;
	}
}
