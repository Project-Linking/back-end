package tukorea.projectlink.global.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.global.exception.BoardException;
import tukorea.projectlink.global.exception.JwtCustomException;
import tukorea.projectlink.global.exception.Oauth2Exception;
import tukorea.projectlink.global.exception.UserException;

@RestControllerAdvice
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommonExceptionHandler {
    /**
     * Request DTO Field Validation 예외 전역처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleArgumentValidation(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        log.error("Field Error:{}", fieldError);
        return CommonResponse.failureWithErrorCode(new FieldErrorCode(null, fieldError.getDefaultMessage()));
    }

    /**
     * 회원 관련 예외 전역처리
     */
    @ExceptionHandler(UserException.class)
    public CommonResponse<?> handleUserException(UserException userException) {
        log.error("User Error:{}", userException.getUserErrorCode());
        return CommonResponse.failureWithErrorCode(userException.getUserErrorCode());
    }

    /**
     * 게시글 관련 예외 전역처리
     */
    @ExceptionHandler(BoardException.class)
    public CommonResponse<?> handleUserException(BoardException boardException) {
        log.error("Board Error:{}", boardException.getBoardErrorCode());
        return CommonResponse.failureWithErrorCode(boardException.getBoardErrorCode());
    }

    /**
     * Jwt 관련 예외 전역처리
     */
    @ExceptionHandler(JwtCustomException.class)
    public CommonResponse<?> handleUserException(JwtCustomException jwtCustomException) {
        log.error("JWT Error:{}", jwtCustomException.getJwtErrorCode());
        return CommonResponse.failureWithErrorCode(jwtCustomException.getJwtErrorCode());
    }

    /**
     * Oauth2 로그인 관련 예외 전역처리
     */
    @ExceptionHandler(Oauth2Exception.class)
    public CommonResponse<?> handleUserException(Oauth2Exception oauth2Exception) {
        log.error("Oauth2 Login Error:{}", oauth2Exception.getOauthErrorCode());
        return CommonResponse.failureWithErrorCode(oauth2Exception.getOauthErrorCode());
    }

    @AllArgsConstructor
    private static class FieldErrorCode implements CommonError {
        private String code;
        private String description;

        @Override
        public String getCode() {
            return null;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
