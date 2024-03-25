package tukorea.projectlink.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.comment.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoard(Board board);
}
