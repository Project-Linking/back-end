package tukorea.projectlink.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.repository.CommentRepository;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseComment createComment(RequestComment request) {
        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId()).orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }
        Comment comment = Comment.builder()
                .user(userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found")))
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

        List<Comment> comment = commentRepository.findAllByBoard(board);

        return comment.stream()
                .map(ResponseComment::new)
                .collect(Collectors.toList());


    }

}
