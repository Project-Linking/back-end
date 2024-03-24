package tukorea.projectlink.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import tukorea.projectlink.board.Board;
import tukorea.projectlink.comment.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoard(Board board);
}
