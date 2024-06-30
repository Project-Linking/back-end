package tukorea.projectlink.board.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.domain.LikeThing;
import tukorea.projectlink.user.domain.User;

public interface LikeThingRepository extends JpaRepository<LikeThing, Long> {
    boolean existsByUserAndBoard(User user, Board board);

    void deleteByUserAndBoard(User user, Board board);

}


