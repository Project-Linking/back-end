package tukorea.projectlink.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.user.dto.InterestsRequest;
import tukorea.projectlink.user.dto.UserSignUpRequest;
import tukorea.projectlink.user.service.UserService;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public CommonResponse<?> signUp(@RequestBody @Valid UserSignUpRequest userSignUpDto) {
        userService.signUp(userSignUpDto);
        log.info("Sign-Up Success : nickname={}", userSignUpDto.nickname());
        return CommonResponse.successWithEmptyData();
    }

    @PostMapping("/interests")
    public CommonResponse<?> saveInterests(@Auth Authentication authentication, @RequestBody InterestsRequest interestsRequest) {
        userService.saveInterests(interestsRequest, authentication);
        return CommonResponse.successWithEmptyData();
    }
}
