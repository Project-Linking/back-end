package tukorea.projectlink.global.jwt.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tukorea.projectlink.global.jwt.JwtConfigProperties;
import tukorea.projectlink.global.jwt.exception.JwtErrorCode;
import tukorea.projectlink.global.jwt.exception.JwtException;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    private static final String USER_UNIQUE_CLAIM = "user_unique_id";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String TOKEN_TYPE = "Bearer ";
    private final JwtConfigProperties props;

    // 액세스 토큰 발급
    public String createAccessToken(String userId) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + props.getAccessExpiration()))
                .withClaim(USER_UNIQUE_CLAIM, userId)
                .sign(Algorithm.HMAC512(props.getSecretKey()));
    }

    // 리프레쉬 토큰 발급
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + props.getRefreshExpiration()))
                .sign(Algorithm.HMAC512(props.getSecretKey()));
    }

    // RTR 방식 적용 , 헤더에 AccessToken 과 RefreshToken 설정 (최초 발급)
    public void setJwtTokenToHeader(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(props.getAccessHeader(), accessToken);
        response.setHeader(props.getRefreshHeader(), refreshToken);
    }

    public void setAccessTokenToHeader(HttpServletResponse response, String accessToken){
        response.setHeader(props.getAccessHeader(),accessToken);
    }

    public Optional<String> extractUserUniqueId(String accessToken) throws JwtException {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(props.getSecretKey()))
                    .build()
                    .verify(accessToken)
                    .getClaim(USER_UNIQUE_CLAIM)
                    .asString());
        } catch (SignatureVerificationException e) {
            throw new JwtException(JwtErrorCode.SIGNATURE_VERIFICATION_FAILED);
        } catch (JWTDecodeException e) {
            throw new JwtException(JwtErrorCode.DECODE_FAILED);
        }
    }

    public Optional<String> extractTokenFromRequestHeader(HttpServletRequest request, String headerType) {
        return Optional.ofNullable(request.getHeader(headerType))
                .map(token -> token.replace(TOKEN_TYPE, ""));
    }

    public boolean isValidToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(props.getSecretKey()))
                    .build()
                    .verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            throw new JwtException(JwtErrorCode.SIGNATURE_VERIFICATION_FAILED);
        } catch (JWTDecodeException e) {
            throw new JwtException(JwtErrorCode.DECODE_FAILED);
        }
    }

}
