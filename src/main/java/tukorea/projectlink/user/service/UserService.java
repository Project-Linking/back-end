package tukorea.projectlink.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.user.domain.Interests;
import tukorea.projectlink.user.domain.InterestsType;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.dto.InterestsRequest;
import tukorea.projectlink.user.dto.UserSignUpRequest;
import tukorea.projectlink.user.exception.UserException;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.List;

import static tukorea.projectlink.user.exception.UserErrorCode.DUPLICATED_DATA_REQUEST;
import static tukorea.projectlink.user.exception.UserErrorCode.USER_NOT_FOUND;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InterestsMapper interestsMapper;
    private final UserMapper userMapper;

    @Transactional
    public void signUp(UserSignUpRequest userSignUpDto) {
        validateInputField(userSignUpDto);
        User user = userMapper.mapFrom(userSignUpDto);
        userRepository.save(user);
    }

    @Transactional
    public void saveInterests(InterestsRequest interestsRequest, UserDetails userdetails) {
        User user = userRepository.findByLoginId(userdetails.getUsername())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        List<Interests> oldInterests = user.getInterests();
        List<InterestsType> newInterests = interestsRequest.interests();

        if (oldInterests.isEmpty()) {
            saveNewInterests(newInterests, user);
        } else {
            updateInterests(oldInterests, newInterests);
        }
    }

    private void updateInterests(List<Interests> oldInterests, List<InterestsType> newInterests) {
        oldInterests.removeAll(
                oldInterests
                        .stream()
                        .filter(oldType -> !newInterests.contains(oldType.getInterestsType()))
                        .toList()
        );
        oldInterests.addAll(
                newInterests
                        .stream()
                        .filter(newType -> !interestsMapper.toInterestsType(oldInterests).contains(newType))
                        .map(Interests::new)
                        .toList()
        );
    }

    private void saveNewInterests(List<InterestsType> newInterests, User user) {
        user.getInterests().addAll(
                newInterests
                        .stream()
                        .map(Interests::new)
                        .toList()
        );
    }

    private void validateInputField(UserSignUpRequest userSignUpDto) throws UserException {
        if (userRepository.findByLoginId(userSignUpDto.loginId()).isPresent()) {
            throw new UserException(DUPLICATED_DATA_REQUEST.changeDefaultDescription("이미 존재하는 아이디입니다."));
        }
        if (userRepository.findByNickname(userSignUpDto.nickname()).isPresent()) {
            throw new UserException(DUPLICATED_DATA_REQUEST.changeDefaultDescription("이미 존재하는 닉네임입니다."));
        }
    }
}
