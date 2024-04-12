package tukorea.projectlink.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserSignUpRequestDto(
        @Size(min = 5, message = "아이디는 5자 이상이여야 합니다.")
        @NotBlank(message = "로그인 아이디는 필수 입력입니다.")
        String loginId,
        @Size(min = 8, message = "비밀번호는 8자 이상이여야 합니다.")
        @NotBlank(message = "비밀번호는 필수 입력입니다.")
        String password,
        @NotBlank(message = "닉네임은 필수 입력입니다.")
        String nickname) {
}
