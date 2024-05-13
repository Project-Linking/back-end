package tukorea.projectlink.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSignUpRequest(
        @Size(min = 5, max = 30, message = "아이디는 5자 이상 30자 이하여야 합니다.")
        @NotBlank(message = "로그인 아이디는 필수 입력입니다.")
        String loginId,
        @Size(min = 8, max = 30, message = "비밀번호는 8자 이상 30자 이하여야 합니다.")
        @NotBlank(message = "비밀번호는 필수 입력입니다.")
        String password,
        @Size(min = 2, max = 10, message = "닉네임은 5자 이상 10자 이하여야 합니다.")
        @NotBlank(message = "닉네임은 필수 입력입니다.")
        String nickname) {
}
