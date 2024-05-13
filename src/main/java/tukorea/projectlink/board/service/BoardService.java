package tukorea.projectlink.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.BoardMainResponse;
import tukorea.projectlink.board.dto.BoardRequest;
import tukorea.projectlink.board.dto.BoardDetailsResponse;
import tukorea.projectlink.global.exception.BoardException;
import tukorea.projectlink.board.respository.BoardRepository;
import tukorea.projectlink.comment.service.CommentService;
import tukorea.projectlink.user.service.UserService;

import java.util.List;
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
                .deadline(boardRequest.deadline())
                .build();

        Board save = boardRepository.save(board);

        BoardDetailsResponse boardDetailsResponse = BoardDetailsResponse.builder()
                .id(save.getId())
                .userId(save.getUser().getId())
                .title(save.getTitle())
                .content(save.getContent())
                .deadline(save.getDeadline())
                .createdAt(save.getCreatedAt())
                .modifiedAt(save.getModifiedAt())
                .build();

        return boardDetailsResponse;
    }

    public List<BoardMainResponse> findAllBoard() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc();
        return boards.stream()
                .map(BoardMainResponse::toResponse)
                .collect(Collectors.toList());
    }

    public BoardDetailsResponse findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        BoardDetailsResponse boardDetailsResponseById = BoardDetailsResponse.builder()
                .id(board.getId())
                .userId(board.getUser().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .deadline(board.getDeadline())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .comments(commentService.getAllCommentByPost(board.getId()))
                .build();

        return boardDetailsResponseById;
    }

    public BoardDetailsResponse updateBoard(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        board.update(boardRequest.title(), boardRequest.content(), boardRequest.deadline());

        Board save = boardRepository.save(board);

        BoardDetailsResponse boardDetailsResponseByIdUpdate = BoardDetailsResponse.builder()
                .id(save.getId())
                .userId(save.getUser().getId())
                .title(save.getTitle())
                .content(save.getContent())
                .deadline(save.getDeadline())
                .createdAt(save.getCreatedAt())
                .modifiedAt(save.getModifiedAt())
                .build();

        return boardDetailsResponseByIdUpdate;
    }

    public void deleteBoard(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));
        boardRepository.deleteById(id);
    }
}

