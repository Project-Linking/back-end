package tukorea.projectlink.jwt.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JwtErrorCode implements CommonError {
    SIGNATURE_VERIFICATION_FAILED("JWT-001", "JWT Signature 불일치"),
    DECODE_FAILED("JWT-002", "JWT 디코딩 실패"),
    MISMATCH_REFRESHTOKEN("JWT-003", "RefreshToken 불일치, 비정상적인 접근"),
    MISMATCH_ACCESSTOKEN("JWT-004", "AccessToken 불일치");
    private final String code;
    private final String description;

    JwtErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
