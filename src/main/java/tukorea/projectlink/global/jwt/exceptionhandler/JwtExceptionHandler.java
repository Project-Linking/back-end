package tukorea.projectlink.global.jwt.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.global.jwt.exception.JwtException;

@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler({JwtException.class})
    public CommonResponse<?> handleAuthenticationException(JwtException e) {
        return CommonResponse.failureWithErrorCode(e.getJwtErrorCode());
    }
}
