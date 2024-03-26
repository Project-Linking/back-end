package tukorea.projectlink.comment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.repository.CommentRepository;
import tukorea.projectlink.user.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CommentService commentService;

    @Test
    void createComment() {
        //given
        User user=User.signupBuilder()
                .loginId("loginId")
                .password("password")
                .passwordEncoder(passwordEncoder)
                .nickname("nickname")
                .build();

        Board board= Board.builder()
                .title("test")
                .content("content")
                .build();

        Comment comment = Comment.builder()
                .content("test_content")
                .user(user)
                .board(board)
                .build();

        RequestComment requestComment = RequestComment.builder()
                .boardId(1L)
                .userId(1L)
                .content("test_content")
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(Board.builder().build()));
        when(commentRepository.save(any())).thenReturn(comment);

        ResponseComment result = commentService.createComment(requestComment);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(comment.getContent());
        assertThat(result).isEqualTo(comment.getContent());
    }

    @Test
    @DisplayName("댓글 불러오기")
    void getCommentByBoard(){
        //given
        User user=User.signupBuilder()
                .loginId("loginId")
                .password("password")
                .passwordEncoder(passwordEncoder)
                .nickname("nickname")
                .build();

        Board board= Board.builder()
                .title("test")
                .content("content")
                .comments(new ArrayList<>())
                .build();

        Comment comment = Comment.builder()
                .content("test_content")
                .user(user)
                .board(board)
                .build();

        comment.setBoard(board);

        List<Comment> comments=new ArrayList<>();
        comments.add(comment);


        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.findAllByBoard(any())).thenReturn(comments);

        List<ResponseComment> results = commentService.getAllCommentByPost(1L);

        assertThat(results.size()).isEqualTo(1);
    }

}