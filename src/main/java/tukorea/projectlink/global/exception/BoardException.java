package tukorea.projectlink.global.exception;

import lombok.Getter;
import tukorea.projectlink.global.errorcode.BoardErrorCode;

@Getter
public class BoardException extends RuntimeException {
    private final BoardErrorCode boardErrorCode;

    public BoardException(BoardErrorCode boardErrorCode) {
        this.boardErrorCode = boardErrorCode;
    }

}
