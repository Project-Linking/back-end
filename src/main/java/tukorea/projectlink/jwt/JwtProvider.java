package tukorea.projectlink.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProvider {
    private final SecretKey secretKey;
    private final Long accessExpiration;
    private final Long refreshExpiration;

    public JwtProvider(String secretKey, Long accessExpiration, Long refreshExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String createToken(String subject, Long duration) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + duration);
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }
}
