package tukorea.projectlink.global.exception;

import lombok.Getter;
import tukorea.projectlink.global.errorcode.JwtErrorCode;

@Getter
/**
 * JJWT가 정의한 JwtException과 충돌해
 * 클래스명에 예외적으로 Custom 키워드를 함께 사용
 */
public class JwtCustomException extends RuntimeException {
    private final JwtErrorCode jwtErrorCode;

    public JwtCustomException(JwtErrorCode jwtErrorCode) {
        this.jwtErrorCode = jwtErrorCode;
    }
}
