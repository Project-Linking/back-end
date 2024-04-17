package tukorea.projectlink.comment.exception;

import tukorea.projectlink.global.common.CommonError;

public enum CommentErrorCode implements CommonError {


    COMMENT_NOT_FOUND("COMMENT-001", "댓글이 존재하지 않습니다."),
    PARENT_COMMENT_NOTFOUND("COMMENT-001", "댓글을 달려하는 댓글이 존재하지 않습니다.");

    private final String code;
    private String description;

    CommentErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public CommonError changeDefaultDescription(String description) {
        this.description = description;
        return this;
    }
}
