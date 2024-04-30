package tukorea.projectlink.login.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.login.exception.Oauth2Exception;

@RestControllerAdvice
public class Oauth2ExceptionHandler {
    @ExceptionHandler(Oauth2Exception.class)
    public CommonResponse<?> handleUserException(Oauth2Exception oauth2Exception) {
        return CommonResponse.failureWithErrorCode(oauth2Exception.getOauthErrorCode());
    }
}
