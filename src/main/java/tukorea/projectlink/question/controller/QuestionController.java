package tukorea.projectlink.question.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.question.dto.QuestionRequest;
import tukorea.projectlink.question.dto.QuestionResponse;
import tukorea.projectlink.question.service.QuestionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("")
    @Operation(summary = "질문 등록")
    public CommonResponse<QuestionResponse> registerQuestion(@Auth Authentication authentication, QuestionRequest questionRequest) {
        QuestionResponse questionResponse = questionService.registerQuestion(authentication, questionRequest);
        return CommonResponse.successWithData(questionResponse);
    }
}
