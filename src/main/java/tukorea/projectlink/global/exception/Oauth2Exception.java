package tukorea.projectlink.global.exception;

import lombok.Getter;
import tukorea.projectlink.global.common.CommonError;

@Getter
public class Oauth2Exception extends RuntimeException {
    private final CommonError oauthErrorCode;

    public Oauth2Exception(CommonError commonError) {
        this.oauthErrorCode = commonError;
    }
}
