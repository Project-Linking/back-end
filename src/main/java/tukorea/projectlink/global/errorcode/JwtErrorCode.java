package tukorea.projectlink.global.errorcode;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JwtErrorCode implements CommonError {
    INVALID_JWT("JWT-001", "신뢰할 수 없는 JWT입니다."),
    EXPIRED_ACCESS_TOKEN("JWT-002", "만료된 액세스 토큰입니다."),
    EXPIRED_REFRESH_TOKEN("JWT-003", "만료된 리프레쉬 토큰입니다.");
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
