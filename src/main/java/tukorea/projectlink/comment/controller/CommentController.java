package tukorea.projectlink.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.service.CommentService;
import tukorea.projectlink.global.common.CommonResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/private/comment")
    public CommonResponse<ResponseComment> createComment(@Auth Authentication authentication, @RequestBody RequestComment requestComment) {
        return CommonResponse.successWithData(commentService.createComment(authentication, requestComment));
    }

    @GetMapping("/public/comment/{board_id}")
    public CommonResponse<List<ResponseComment>> getAllCommentByBoard(@PathVariable(name = "board_id") Long id) {
        return CommonResponse.successWithData(commentService.getAllCommentByPost(id));
    }

    @PatchMapping("/private/comment/{comment_id}")
    public CommonResponse<ResponseComment> updateComment(@Auth Authentication authentication, @PathVariable(name = "comment_id") Long commentId, @RequestBody RequestComment requestComment) {
        return CommonResponse.successWithData(commentService.updateComment(authentication, commentId, requestComment));
    }

    @DeleteMapping("/private/comment/{comment_id}")
    public ResponseEntity<?> deleteComment(@Auth Authentication authentication, @PathVariable(name = "comment_id") Long commentId) {
        commentService.deleteComment(authentication, commentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
