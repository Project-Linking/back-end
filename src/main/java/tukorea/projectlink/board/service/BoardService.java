package tukorea.projectlink.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.BoardMainResponse;
import tukorea.projectlink.board.dto.BoardRequest;
import tukorea.projectlink.board.dto.BoardDetailsResponse;
import tukorea.projectlink.board.enums.Category;
import tukorea.projectlink.comment.dto.ResponseComment;
import tukorea.projectlink.global.exception.BoardException;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.service.CommentService;
import tukorea.projectlink.user.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static tukorea.projectlink.global.errorcode.BoardErrorCode.BOARD_ID_INVALID;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final CommentService commentService;

    public BoardDetailsResponse createBoard(BoardRequest boardRequest, Authentication auth) {
        Board board = Board.builder()
                .user(userService.getUser(auth.userId()))
                .title(boardRequest.title())
                .content(boardRequest.content())
                .category(boardRequest.category())
                .likeNum(0L)
                .deadline(boardRequest.deadline())
                .build();

        boardRepository.save(board);

        List<ResponseComment> comments = commentService.getAllCommentByPost(board.getId());

        return BoardDetailsResponse.toResponseDetails(board, comments);
    }

    public Page<BoardMainResponse> findAllBoard(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(pageable);

        return boards.map(BoardMainResponse::toResponseMain);
    }

    public Page<BoardMainResponse> findBoardBySomething(String title, String nickname,
                                                        Set<Category> category, Pageable pageable) {
        Page<Board> boards;
        if (title != null) {
            boards = boardRepository.findByTitleContaining(title, pageable);
        } else if (nickname != null) {
            boards = boardRepository.findByUserNicknameContaining(nickname, pageable);
        } else if (category != null) {
            boards = boardRepository.findByCategoryIn(category, pageable);
        } else {
            boards = boardRepository.findAllByOrderByModifiedAtDesc(pageable);
        }
        return boards.map(BoardMainResponse::toResponseMain);
    }

    public BoardDetailsResponse findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        List<ResponseComment> comments = commentService.getAllCommentByPost(board.getId());

        return BoardDetailsResponse.toResponseDetails(board, comments);
    }

    public BoardDetailsResponse updateBoard(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        board.update(boardRequest.title(), boardRequest.content(), boardRequest.deadline(), boardRequest.category());

        Board save = boardRepository.save(board);

        List<ResponseComment> comments = commentService.getAllCommentByPost(board.getId());

        return BoardDetailsResponse.toResponseDetails(save, comments);
    }

    public void BoardLikeIncrease(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        board.LikeIncrease(id);
    }

    public void BoardLikeDecrease(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        board.LikeDecrease(id);
    }

    public void deleteBoard(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));
        boardRepository.deleteById(id);
    }
}

