package tukorea.projectlink.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.user.Role;
import tukorea.projectlink.user.User;
import tukorea.projectlink.user.dto.UserSignUpDto;
import tukorea.projectlink.user.repository.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        validateInputField(userSignUpDto);
        User user = User.builder()
                .loginId(userSignUpDto.getLoginId())
                .password(userSignUpDto.getPassword())
                .passwordEncoder(passwordEncoder)
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    private void validateInputField(UserSignUpDto userSignUpDto) throws Exception {
        if(userRepository.findByLoginId(userSignUpDto.getLoginId()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
    }
}
