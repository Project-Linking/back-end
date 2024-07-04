package tukorea.projectlink.login.dto;

import tukorea.projectlink.global.jwt.UserToken;

public record LoginResponse(UserToken userToken, String nickname) {
}
