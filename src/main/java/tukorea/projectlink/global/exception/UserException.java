package tukorea.projectlink.global.exception;

import lombok.Getter;
import tukorea.projectlink.global.common.CommonError;

@Getter
public class UserException extends RuntimeException {
    private final CommonError userErrorCode;

    public UserException(CommonError userErrorCode) {
        this.userErrorCode = userErrorCode;
    }
}
