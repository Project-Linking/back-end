package tukorea.projectlink.user.exceptionhandler;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.global.common.CommonError;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.user.exception.UserErrorCode;
import tukorea.projectlink.user.exception.UserException;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleArgumentValidation(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String errorMessage = fieldError.getDefaultMessage();
        CommonError argumentError = UserErrorCode.ARGUMENT_NOT_VALID.changeDefaultDescription(errorMessage);
        return CommonResponse.failureWithErrorCode(argumentError);
    }

    @ExceptionHandler(UserException.class)
    public CommonResponse<?> handleUserException(UserException userException) {
        return CommonResponse.failureWithErrorCode(userException.getUserErrorCode());
    }
}
