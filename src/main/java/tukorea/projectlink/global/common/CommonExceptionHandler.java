package tukorea.projectlink.global.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.board.exception.BoardException;
import tukorea.projectlink.jwt.exception.JwtCustomException;
import tukorea.projectlink.login.exception.Oauth2Exception;
import tukorea.projectlink.user.exception.UserException;

@RestControllerAdvice
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommonExceptionHandler {
    /**
     * Request DTO Field Validation 예외 전역처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleArgumentValidation(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        log.error("Field Error:{}", fieldError);
        return fieldError.getDefaultMessage();
    }

    /**
     * 회원 관련 예외 전역처리
     */
    @ExceptionHandler(UserException.class)
    public CommonResponse<?> handleUserException(UserException userException) {
        return CommonResponse.failureWithErrorCode(userException.getUserErrorCode());
    }

    /**
     * 게시글 관련 예외 전역처리
     */
    @ExceptionHandler(BoardException.class)
    public CommonResponse<?> handleUserException(BoardException BoardException) {
        return CommonResponse.failureWithErrorCode(BoardException.getBoardErrorCode());
    }

    /**
     * Jwt 관련 예외 전역처리
     */
    @ExceptionHandler(JwtCustomException.class)
    public CommonResponse<?> handleUserException(JwtCustomException jwtCustomException) {
        return CommonResponse.failureWithErrorCode(jwtCustomException.getJwtErrorCode());
    }

    /**
     * Oauth2 로그인 관련 예외 전역처리
     */
    @ExceptionHandler(Oauth2Exception.class)
    public CommonResponse<?> handleUserException(Oauth2Exception oauth2Exception) {
        return CommonResponse.failureWithErrorCode(oauth2Exception.getOauthErrorCode());
    }
}
