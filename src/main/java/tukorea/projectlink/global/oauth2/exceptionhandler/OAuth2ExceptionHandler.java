package tukorea.projectlink.global.oauth2.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.user.exception.UserException;

@RestControllerAdvice
public class OAuth2ExceptionHandler {

    @ExceptionHandler({UserException.class})
    public CommonResponse<?> handleAuthenticationException(UserException e) {
        return CommonResponse.failureWithErrorCode(e.getUserErrorCode());
    }
}
