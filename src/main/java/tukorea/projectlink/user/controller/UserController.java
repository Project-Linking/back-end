package tukorea.projectlink.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.user.dto.UserSignUpDto;
import tukorea.projectlink.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/oauth/sign-up")
    public String oauth2(){
        return "oauth2";
    }

    @GetMapping("/login/oauth2/code/kakao")
    public String success() {
        return "성공";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        log.info("회원 가입 성공");
        return "회원가입 성공";
    }

    @GetMapping("/whatever")
    public String whatever(){
        return "시큐리티 성공";
    }
}
