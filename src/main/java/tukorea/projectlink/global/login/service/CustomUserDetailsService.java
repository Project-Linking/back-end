package tukorea.projectlink.global.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User myUser = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
        return org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLoginId())
                .password(myUser.getPassword())
                .roles(myUser.getRole().getKey())
                .build();
    }
}
