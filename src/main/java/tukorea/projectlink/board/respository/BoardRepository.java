package tukorea.projectlink.board.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.enums.Category;

import java.util.Collection;
import java.util.Set;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByOrderByModifiedAtDesc(Pageable pageable);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    Page<Board> findByUserNicknameContaining(String keyword, Pageable pageable);

    Page<Board> findByCategoryIn(Set<Category> category, Pageable pageable);

}


