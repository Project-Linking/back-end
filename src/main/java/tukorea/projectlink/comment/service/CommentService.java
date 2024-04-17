package tukorea.projectlink.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.CommentRequest;
import tukorea.projectlink.comment.dto.CommentResponse;
import tukorea.projectlink.comment.exception.CommentErrorCode;
import tukorea.projectlink.comment.exception.CommentException;
import tukorea.projectlink.comment.repository.CommentRepository;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponse createComment(UserDetails userDetails, CommentRequest request) {
        Comment parent = null;
        if (request.getParentId() != null) {
            parent = getCommentByParentId(request.getParentId());
        }
        Comment comment = Comment.builder()
                .user(userRepository.findByLoginId(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found")))
                .board(boardRepository.findById(request.getBoardId()).orElseThrow(() -> new RuntimeException("Board not found")))
                .parent(parent)
                .content(request.getContent())
                .build();

        if (parent != null) {
            parent.getReplies().add(comment);
        }

        Comment save = commentRepository.save(comment);

        return new CommentResponse(save);
    }

    public List<CommentResponse> getAllCommentByPost(Long postId) {
        Board board = boardRepository.findById(postId).orElseThrow(() -> new RuntimeException("Board not found"));

        List<Comment> comments = commentRepository.findAllByBoard(board);

        return comments.stream()
                .filter(comment -> comment.getParent() == null)
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public CommentResponse updateComment(UserDetails userDetails, Long commentId, CommentRequest commentRequest) {
        Comment comment = getCommentById(commentId);

        User user = userRepository.findByLoginId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!comment.getUser().equals(user)) {
            throw new IllegalStateException("댓글을 수정할 권한이 없습니다.");
        }

        comment.updateComment(commentRequest.getContent());

        return new CommentResponse(comment);
    }

    public void deleteComment(UserDetails userDetails, Long commentId) {
        Comment comment = getCommentById(commentId);
        User user = userRepository.findByLoginId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!comment.getUser().equals(user)) {
            throw new IllegalStateException("댓글을 수정할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    /*
    DB 접근 메서드
     */
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND.changeDefaultDescription("댓글이 존재하지 않습니다.")));
    }

    public Comment getCommentByParentId(Long parentId) {
        return commentRepository.findById(parentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.PARENT_COMMENT_NOTFOUND.changeDefaultDescription("상위댓글이 존재하지 않습니다.")));
    }

}
