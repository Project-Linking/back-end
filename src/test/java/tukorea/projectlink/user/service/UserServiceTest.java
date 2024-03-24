package tukorea.projectlink.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


class UserServiceTest {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    UserService userService = new UserService(,passwordEncoder);

    @Test
    @DisplayName("원본 패스워드는 암호화된 패스워드와 일치해야한다.")
    void Password_Crypto(){
        String password = "112560at7x23";
        String encodedPassword = passwordEncoder.encode(password);
        Assertions.assertThat(passwordEncoder.matches(password,encodedPassword)).isEqualTo(true);
    }

    @Test
    @DisplayName("")
    void login(){

    }
}