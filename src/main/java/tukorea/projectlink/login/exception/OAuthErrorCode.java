package tukorea.projectlink.login.exception;

import tukorea.projectlink.global.common.CommonError;

public enum OAuthErrorCode implements CommonError {
    CAN_NOT_FIND_OAUTH_PROVIDER("OAUTH-001", "Oauth Provider를 찾을 수 없습니다."),
    INVALID_OAUTH_AUTHORIZATION_CODE("OAUTH-002", "잘못된 인가코드 입니다.");
    private final String code;
    private String description;

    OAuthErrorCode(String code, String description) {
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
