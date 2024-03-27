package tukorea.projectlink.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.user.dto.UserSignUpDto;
import tukorea.projectlink.user.service.UserService;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/login/oauth2/code/kakao")
    public String success() {
        return "성공";
    }

    @PostMapping("/public/sign-up")
    public CommonResponse<?> signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        log.info("Sign-Up Success : nickname={}",userSignUpDto.getNickname());
        return CommonResponse.successWithEmptyData();
    }
}
