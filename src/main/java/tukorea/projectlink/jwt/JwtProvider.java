package tukorea.projectlink.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import tukorea.projectlink.jwt.configproperties.JwtProperties;
import tukorea.projectlink.jwt.exception.JwtCustomException;
import tukorea.projectlink.jwt.exception.JwtErrorCode;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
    private final JwtProperties props;
    private final SecretKey secretKey;

    public JwtProvider(JwtProperties props) {
        this.props = props;
        this.secretKey = Keys.hmacShaKeyFor(props.secretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String create(String subject, Long duration) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + duration);
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public void checkAccessToken(String accessToken) {
        try {
            parse(accessToken);
        } catch (ExpiredJwtException e) {
            throw new JwtCustomException(JwtErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (JwtException e) {
            throw new JwtCustomException(JwtErrorCode.INVALID_JWT);
        }
    }

    public void checkRefreshToken(String refreshToken) {
        try {
            parse(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new JwtCustomException(JwtErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (JwtException e) {
            throw new JwtCustomException(JwtErrorCode.EXPIRED_ACCESS_TOKEN);
        }
    }

    public String getSubject(String token) {
        return parse(token)
                .getPayload()
                .getSubject();
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
