package tukorea.projectlink.user.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import tukorea.projectlink.global.common.CommonError;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserErrorCode implements CommonError {

    ARGUMENT_NOT_VALID("USER-001", "MAL_INPUT"),
    DUPLICATED_DATA_REQUEST("USER-002", "DUPLICATED_DATA");

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
