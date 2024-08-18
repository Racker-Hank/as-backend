package vnu.uet.AppointmentScheduler.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalRestExceptionHandler {
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException exc) {
		return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({
		AuthorizationDeniedException.class,
		UsernameNotFoundException.class
	})
	public ResponseEntity<String> handleAuthorizationDeniedException(AuthorizationDeniedException exc) {
		return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({
		IllegalArgumentException.class,
		MethodArgumentTypeMismatchException.class,
		MissingServletRequestParameterException.class,
		HttpMessageNotReadableException.class
	})
	public ResponseEntity<String> handleBadRequestException(Exception exc) {
		log.error(exc.toString(), exc.getMessage());
		return new ResponseEntity<>(
			exc.getCause() != null ? exc.getCause().getMessage() : exc.getMessage(),
			HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler({
		DataIntegrityViolationException.class
	})
	public ResponseEntity<String> handleConflictException(Exception exc) {
		log.error(exc.toString(), exc.getMessage());
		return new ResponseEntity<>(
			exc.getCause() != null ? exc.getCause().getMessage() : exc.getMessage(),
			HttpStatus.CONFLICT
		);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException exc) {
		log.error(exc.toString(), exc.getMessage());
		return new ResponseEntity<>(exc.getMessage(), exc.getStatusCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleUnexpectedException(Exception exc) {
		log.error(exc.toString(), exc.getMessage());
		return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
