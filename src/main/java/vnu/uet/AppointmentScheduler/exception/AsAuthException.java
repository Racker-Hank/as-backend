package vnu.uet.AppointmentScheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AsAuthException extends RuntimeException{
	public AsAuthException(String message) {
		super(message);
	}
}
