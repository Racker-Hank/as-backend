package vnu.uet.AppointmentScheduler.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vnu.uet.AppointmentScheduler.config.security.UserDetailsImpl;
import vnu.uet.AppointmentScheduler.constants.UserRole;
import vnu.uet.AppointmentScheduler.model.user.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

	@Value("${JWT_SECRET}")
	private String secret;

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

    public boolean validateToken(String jwtToken, UserDetailsImpl userDetails) {
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
		return createToken(user.getId(), user.getEmail(), user.getUserRole());
	}

    private String createToken(UUID id, String email, UserRole userRole) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("email", email)
                .claim("role", userRole.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)
                )
                .signWith(getSignKey())
                .compact();
    }
}
