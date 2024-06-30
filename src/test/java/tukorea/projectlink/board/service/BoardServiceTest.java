package tukorea.projectlink.board.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.comment.repository.CommentRepository;
import tukorea.projectlink.user.Role;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;

import java.util.List;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User("login", "paswword", "nickname", Role.USER, null, null, null, null);
        User save1 = userRepository.save(user);

        for (int i = 0; i < 3; i++) {
            Board board = Board.builder()
                    .user(save1)
                    .title("제목" + i)
                    .content("내용" + i)
                    .build();

            Board save = boardRepository.save(board);

            for (int j = 0; j < 3; j++) { // 각 게시물에 대해 여러 댓글 추가
                Comment comment = Comment.builder()
                        .board(save)
                        .user(save1)
                        .content("댓글" + j)
                        .build();
                commentRepository.save(comment);
            }
        }
    }

    @Test
    public void N_plus_One() {
        em.flush();
        em.clear();

        System.out.println("=== N+1 문제 발생 시나리오 ===");

        Pageable pageable = PageRequest.of(1, 5, Sort.by("modifiedAt").descending());

        Page<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(pageable);
        for (Board board : boards) {
            System.out.println("board_id=" + board.getId());
            System.out.println("댓글 수: " + board.getComments().size());
        }
    }
}