package tukorea.projectlink.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.repository.CommentRepository;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;
import tukorea.projectlink.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public ResponseComment createComment(Authentication authentication, RequestComment request) {
        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId()).orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }
        Comment comment = Comment.builder()
                .user(userService.getUser(authentication.userId()))
                .board(boardRepository.findById(request.getBoardId()).orElseThrow(() -> new RuntimeException("Board not found")))
                .parent(parent)
                .content(request.getContent())
                .build();

        if (parent != null) {
            parent.getReplies().add(comment);
        }

        Comment save = commentRepository.save(comment);

        return new ResponseComment(save);
    }

    public List<ResponseComment> getAllCommentByPost(Long postId) {
        Board board = boardRepository.findById(postId).orElseThrow(() -> new RuntimeException("Board not found"));

        List<Comment> comments = commentRepository.findAllByBoard(board);

        return comments.stream()
                .filter(comment -> comment.getParent() == null)
                .map(ResponseComment::new)
                .collect(Collectors.toList());
    }

    public ResponseComment updateComment(Authentication authentication, Long commentId, RequestComment requestComment) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not Found"));

        User user = userService.getUser(authentication.userId());

        if (!comment.getUser().equals(user)) {
            throw new IllegalStateException("댓글을 수정할 권한이 없습니다.");
        }

        comment.updateComment(requestComment.getContent());

        return new ResponseComment(comment);
    }

    public void deleteComment(Authentication authentication, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not Found"));
        User user = userService.getUser(authentication.userId());

        if (!comment.getUser().equals(user)) {
            throw new IllegalStateException("댓글을 수정할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

}
