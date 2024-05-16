package tukorea.projectlink.board.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.BoardDetailsResponse;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
