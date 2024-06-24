package tukorea.projectlink.login.dto;

import tukorea.projectlink.jwt.UserToken;

public record Oauth2LoginResponse(
        UserToken userToken,
        String nickname,
        String imgUri
) {
}
