package tukorea.projectlink.user.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserErrorCode implements CommonError {

    ARGUMENT_NOT_VALID("USER-001", "MAL_INPUT"),
    DUPLICATED_DATA_REQUEST("USER-002", "DUPLICATED_DATA"),
    USER_NOT_FOUND("USER-003", "회원을 찾을 수 없습니다."),
    INTERESTS_REQUEST_NOT_VALID("USER-004", "관심 분야는 최대 3개까지 등록이 가능합니다.");

    private final String code;
    private String description;

    UserErrorCode(String code, String description) {
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
