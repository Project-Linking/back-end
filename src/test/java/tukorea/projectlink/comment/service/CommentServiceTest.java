package tukorea.projectlink.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.dto.RequestComment;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.comment.repository.CommentRepository;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private UserDetails userA;
    @Mock
    private UserDetails userB;

    @InjectMocks
    private CommentService commentService;

    private User user1;
    private User user2;
    private Board board;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user1 = createUser("userA", "nickname1");
        user2 = createUser("userB", "nickname2");
        board = createBoard("test", "content");
        comment = createComment("write_user1", user1, board);
    }

    private User createUser(String loginId, String nickname) {
        return User.builder()
                .loginId(loginId)
                .password("password")
                .nickname(nickname)
                .build();
    }

    private Board createBoard(String title, String content) {
        return Board.builder()
                .title(title)
                .content(content)
                .comments(new ArrayList<>())
                .build();
    }

    private Comment createComment(String content, User user, Board board) {
        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .board(board)
                .build();
        comment.setBoard(board);
        return comment;
    }

    @Test
    @DisplayName("성공: 댓글작성")
    void createComment() {
        //given
        when(userA.getUsername()).thenReturn("loginId");
        RequestComment requestComment = RequestComment.builder()
                .boardId(1L)
                .content("test_content")
                .build();

        //when
        when(userRepository.findByLoginId(any())).thenReturn(Optional.ofNullable(this.user1));
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(Board.builder().build()));
        when(commentRepository.save(any())).thenReturn(comment);

        ResponseComment result = commentService.createComment(userA, requestComment);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(comment.getContent());
        assertThat(result.getContent()).isEqualTo(comment.getContent());
    }

    @Test
    @DisplayName("성공: 댓글 불러오기")
    void getCommentByBoard() {
        //given
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        //when
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(commentRepository.findAllByBoard(any())).thenReturn(comments);

        List<ResponseComment> results = commentService.getAllCommentByPost(1L);

        //then
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("성공: 댓글 수정하기")
    void updateComment() {
        //given
        when(userA.).thenReturn("loginId");

        RequestComment requestComment = RequestComment.builder()
                .content("test_content")
                .build();

        //when
        when(userRepository.findByLoginId(any())).thenReturn(Optional.ofNullable(this.user1));
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        ResponseComment result = commentService.updateComment(userA, 1L, requestComment);

        //then
        assertThat(result.getContent()).isEqualTo("test_content");
    }

    @Test
    @DisplayName("실패: 유저A가 작성한댓글을 유저B가 수정할경우")
    void updateCommentThrowError() {
        //given
        RequestComment requestComment = RequestComment.builder()
                .content("write_user1")
                .build();
        //when
        when(userRepository.findByLoginId(any())).thenReturn(Optional.ofNullable(user2));
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        //then
        assertThrows(IllegalStateException.class, () -> commentService.updateComment(userB, 1L, requestComment));
    }

    @Test
    @DisplayName("실패: 유저B가 유저A가 작성한 댓글삭제")
    void deleteComment() {
        //when
        when(userRepository.findByLoginId(any())).thenReturn(Optional.ofNullable(user2));
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        //then
        assertThrows(IllegalStateException.class, () -> commentService.deleteComment(userB, 1L));
    }

}