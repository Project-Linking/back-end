package tukorea.projectlink.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public CommonResponse<ResponseComment> createComment(@RequestBody RequestComment requestComment) {
        return CommonResponse.successWithData(commentService.createComment(requestComment));
    }

    @GetMapping("/{board_id}")
    public CommonResponse<List<ResponseComment>> getAllCommentByBoard(@PathVariable(name = "board_id") Long id) {
        return CommonResponse.successWithData(commentService.getAllCommentByPost(id));
    }

    @PatchMapping("/{comment_id}")
    public CommonResponse<ResponseComment> updateComment(@PathVariable(name = "comment_id") Long commentId, @RequestBody RequestComment requestComment) {
        return CommonResponse.successWithData(commentService.updateComment(commentId, requestComment));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "comment_id") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
