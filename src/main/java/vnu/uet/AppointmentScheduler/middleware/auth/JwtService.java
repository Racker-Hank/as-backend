package vnu.uet.AppointmentScheduler.middleware.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
	public static final String ACCESS_TOKEN_COOKIE_KEY = "access_token";

	@Value("${CONTEXT_PATH}")
	private String contextPath;

	@Value("${JWT_SECRET}")
	private String secret;

	@Value("${JWT_EXPIRATION}")
	private Long jwtExpiration;

	public UUID extractId(String jwtToken) {
		String subject = extractClaim(jwtToken, Claims::getSubject);
		return UUID.fromString(subject);
	}

	public String extractEmail(String jwtToken) {
		Claims claims = extractAllClaims(jwtToken);
		return claims.get("email").toString();
	}

	public UserRole extractRole(String jwtToken) {
		Claims claims = extractAllClaims(jwtToken);
		String roleString = (String) claims.get("role");
		return UserRole.valueOf(roleString);
	}

	private <T> T extractClaim(
		String jwtToken,
		Function<Claims, T> claimsResolver
	) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String jwtToken) {
		return Jwts.parser()
			.verifyWith(getSignKey())
			.build()
			.parseSignedClaims(jwtToken)
			.getPayload();
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean validateToken(String jwtToken, User userDetails) {
		final UUID id = extractId(jwtToken);
		return id.equals(userDetails.getId()) && !isTokenExpired(jwtToken);
	}

	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}

	private Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}

	public String generateToken(User user) {
		return createToken(user.getId(), user.getEmail(), user.getRole());
	}

	private String createToken(UUID id, String email, UserRole role) {
		return Jwts.builder()
			.subject(id.toString())
			.claim("email", email)
			.claim("role", role.toString())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(
				new Date(System.currentTimeMillis() + 1000 * jwtExpiration)
			)
			.signWith(getSignKey())
			.compact();
	}

	public void saveAccessTokenToCookie(
		String token,
		HttpServletResponse response
	) {
		ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_KEY, token)
			.httpOnly(true)
			.secure(false)
			.path(contextPath)
			.maxAge(jwtExpiration)
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	public boolean clearAccessTokenFromCookie(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			log.debug(String.valueOf(cookies.length));
			for (Cookie cookie : cookies) {
				if (ACCESS_TOKEN_COOKIE_KEY.equals(cookie.getName())) {
					cookie.setValue(null); // Clear the value
					cookie.setMaxAge(0);   // Set cookie to expire immediately
					cookie.setPath(contextPath);
					cookie.setHttpOnly(true);
					cookie.setSecure(false);
					response.addCookie(cookie);
					return true;
				}
			}
		}
		return false;
	}
}
