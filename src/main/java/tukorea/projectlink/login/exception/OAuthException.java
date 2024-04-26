package tukorea.projectlink.login.exception;

import lombok.RequiredArgsConstructor;
import tukorea.projectlink.global.common.CommonError;

@RequiredArgsConstructor
public class OAuthException extends RuntimeException {
    private final CommonError oauthErrorCode;
}
