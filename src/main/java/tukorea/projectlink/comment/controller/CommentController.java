package tukorea.projectlink.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.comment.dto.CommentRequest;
import tukorea.projectlink.comment.dto.CommentResponse;
import tukorea.projectlink.comment.service.CommentService;
import tukorea.projectlink.global.common.CommonResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/private/comment")
    public CommonResponse<CommentResponse> createComment(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CommentRequest commentRequest) {
        return CommonResponse.successWithData(commentService.createComment(userDetails, commentRequest));
    }

    @GetMapping("/public/comment/{board_id}")
    public CommonResponse<List<CommentResponse>> getAllCommentByBoard(@PathVariable(name = "board_id") Long id) {
        return CommonResponse.successWithData(commentService.getAllCommentByPost(id));
    }

    @PatchMapping("/private/comment/{comment_id}")
    public CommonResponse<CommentResponse> updateComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "comment_id") Long commentId, @Valid @RequestBody CommentRequest commentRequest) {
        return CommonResponse.successWithData(commentService.updateComment(userDetails, commentId, commentRequest));
    }

    @DeleteMapping("/private/comment/{comment_id}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "comment_id") Long commentId) {
        commentService.deleteComment(userDetails, commentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
