package vnu.uet.AppointmentScheduler.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalRestExceptionHandler {
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException exc) {
		return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException exc) {
		log.error(exc.toString());
		return new ResponseEntity<>(exc.getMessage(), exc.getStatusCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleUnexpectedException(Exception exc) {
		log.error(exc.toString());
		return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
