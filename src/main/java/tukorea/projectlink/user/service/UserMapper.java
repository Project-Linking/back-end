package tukorea.projectlink.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tukorea.projectlink.user.Role;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.dto.UserSignUpRequest;
import tukorea.projectlink.user.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User mapFrom(UserSignUpRequest dto) {
        String encodedPassword = encryptPassword(dto);
        return User.builder()
                .loginId(dto.loginId())
                .password(encodedPassword)
                .nickname(dto.nickname())
                .role(Role.USER)
                .build();
    }

    private String encryptPassword(UserSignUpRequest dto) {
        return passwordEncoder.encode(dto.password());
    }
}
