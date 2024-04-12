package tukorea.projectlink.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.user.Role;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.dto.UserSignUpRequestDto;
import tukorea.projectlink.user.exception.UserErrorCode;
import tukorea.projectlink.user.exception.UserException;
import tukorea.projectlink.user.repository.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpRequestDto userSignUpDto) throws Exception {
        validateInputField(userSignUpDto);
        User user = User.builder()
                .loginId(userSignUpDto.loginId())
                .password(userSignUpDto.password())
                .nickname(userSignUpDto.nickname())
                .role(Role.USER)
                .build();
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    private void validateInputField(UserSignUpRequestDto userSignUpDto) throws UserException {
        if (userRepository.findByLoginId(userSignUpDto.loginId()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATED_DATA_REQUEST.changeDefaultDescription("이미 존재하는 아이디입니다."));
        }
        if (userRepository.findByNickname(userSignUpDto.nickname()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATED_DATA_REQUEST.changeDefaultDescription("이미 존재하는 닉네임입니다."));
        }
    }
}
