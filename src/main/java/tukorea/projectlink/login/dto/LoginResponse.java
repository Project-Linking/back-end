package tukorea.projectlink.login.dto;

import tukorea.projectlink.jwt.UserToken;

public record LoginResponse(UserToken userToken, String nickname) {
}
