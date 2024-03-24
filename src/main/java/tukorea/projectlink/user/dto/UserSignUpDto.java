package tukorea.projectlink.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String loginId;
    private String password;
    private String nickname;
}
