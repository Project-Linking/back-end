package tukorea.projectlink.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.service.CommentService;
import tukorea.projectlink.global.common.CommonResponse;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping()
    public CommonResponse<ResponseComment> createComment(@RequestBody RequestComment requestComment){
        return CommonResponse.successWithData(commentService.createComment(requestComment));
    }

    @GetMapping("/{board_id}")
    public CommonResponse<List<ResponseComment>> getAllCommentByBoard(@PathVariable(name = "board_id")Long id){
        return CommonResponse.successWithData(commentService.getAllCommentByPost(id));
    }


}
