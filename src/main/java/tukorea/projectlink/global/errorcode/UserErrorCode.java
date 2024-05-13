package tukorea.projectlink.global.errorcode;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserErrorCode implements CommonError {

    ARGUMENT_NOT_VALID("USER-001", "잘못된 입력형식 입니다."),
    DUPLICATED_DATA_REQUEST("USER-002", "중복된 데이터 입니다."),
    USER_NOT_FOUND("USER-003", "회원을 찾을 수 없습니다."),
    INTERESTS_REQUEST_NOT_VALID("USER-004", "관심 분야는 최대 3개까지 등록이 가능합니다."),
    INVALID_REQUEST("USER-005", "재요청 필요"),
    INVALID_PASSWORD("USER-006", "잘못된 비밀번호 입니다."),
    USER_REFRESH_TOKEN_NOT_FOUND("USER-007", "토큰과 일치하는 회원이 없습니다."),
    USER_ACCESS_TOKEN_NOT_FOUND("USER-008", "헤더에 토큰이 없습니다.");

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
