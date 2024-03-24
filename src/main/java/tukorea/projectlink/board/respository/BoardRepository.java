package tukorea.projectlink.board.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
