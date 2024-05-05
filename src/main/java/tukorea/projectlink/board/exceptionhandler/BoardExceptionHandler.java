package tukorea.projectlink.board.exceptionhandler;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tukorea.projectlink.board.exception.BoardException;
import tukorea.projectlink.global.common.CommonResponse;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardException.class)
    public CommonResponse<?> handleUserException(BoardException BoardException) {
        return CommonResponse.failureWithErrorCode(BoardException.getBoardErrorCode());
    }
}
