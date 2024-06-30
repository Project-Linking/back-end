package tukorea.projectlink.board.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.domain.LikeThing;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.board.respository.LikeThingRepository;
import tukorea.projectlink.global.exception.BoardException;
import tukorea.projectlink.global.exception.UserException;
import tukorea.projectlink.user.domain.User;
import tukorea.projectlink.user.repository.UserRepository;
import tukorea.projectlink.user.service.UserService;

import static tukorea.projectlink.global.errorcode.BoardErrorCode.BOARD_ID_INVALID;
import static tukorea.projectlink.global.errorcode.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeThingService {

    private final LikeThingRepository likeThingRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;


    public void likeThing(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        if (!likeThingRepository.existsByUserAndBoard(user,board)) {

            LikeThing likeThing = LikeThing.builder().
                    user(user).
                    board(board).
                    build();

            boardService.BoardLikeIncrease(boardId);

            likeThingRepository.save(likeThing);
        }
    }

    public void unlikeThing(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        if (likeThingRepository.existsByUserAndBoard(user,board)) {

            boardService.BoardLikeDecrease(boardId);

            likeThingRepository.deleteByUserAndBoard(user,board);
        }
    }
}
