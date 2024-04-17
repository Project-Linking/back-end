package tukorea.projectlink.comment.exception;

import lombok.Getter;
import tukorea.projectlink.global.common.CommonError;

@Getter
public class CommentException extends RuntimeException {
    private final CommonError commentErrorCode;

    public CommentException(CommonError commentErrorCode) {
        this.commentErrorCode = commentErrorCode;
    }
}
