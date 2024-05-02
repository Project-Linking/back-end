package tukorea.projectlink.board.exception;

import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {
    private final BoardErrorCode boardErrorCode;

    public BoardException(BoardErrorCode boardErrorCode) {
        this.boardErrorCode = boardErrorCode;
    }

}
