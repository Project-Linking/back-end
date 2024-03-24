package tukorea.projectlink.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping()
    public ResponseComment createComment(@RequestBody RequestComment requestComment){
        return commentService.createComment(requestComment);
    }

    @GetMapping("/{board_id}")
    public List<ResponseComment> getAllCommentByBoard(@PathVariable(name = "board_id")Long id){
        return commentService.getAllCommentByPost(id);
    }

}
