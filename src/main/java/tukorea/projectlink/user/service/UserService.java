package tukorea.projectlink.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.dto.UserSignUpRequest;
import tukorea.projectlink.user.exception.UserErrorCode;
import tukorea.projectlink.user.exception.UserException;
import tukorea.projectlink.user.repository.UserRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void signUp(UserSignUpRequest userSignUpDto) {
        validateInputField(userSignUpDto);
        User user = userMapper.mapFrom(userSignUpDto);
        userRepository.save(user);
    }

    private void validateInputField(UserSignUpRequest userSignUpDto) throws UserException {
        if (userRepository.findByLoginId(userSignUpDto.loginId()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATED_DATA_REQUEST.changeDefaultDescription("이미 존재하는 아이디입니다."));
        }
        if (userRepository.findByNickname(userSignUpDto.nickname()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATED_DATA_REQUEST.changeDefaultDescription("이미 존재하는 닉네임입니다."));
        }
    }
}
