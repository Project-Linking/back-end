package tukorea.projectlink.login.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Oauth2ErrorCode implements CommonError {
    CAN_NOT_FIND_OAUTH_PROVIDER("OAUTH-001", "Oauth Provider를 찾을 수 없습니다."),
    INVALID_OAUTH_AUTHORIZATION_CODE("OAUTH-002", "잘못된 인가코드 입니다.");
    private final String code;
    private String description;

    Oauth2ErrorCode(String code, String description) {
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
