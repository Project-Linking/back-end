package tukorea.projectlink.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.RequestBoard;
import tukorea.projectlink.board.dto.ResponseBoard;
import tukorea.projectlink.board.exception.BoardException;
import tukorea.projectlink.board.respository.BoardRepository;

import java.util.List;

import static tukorea.projectlink.board.exception.BoardErrorCode.BOARD_ID_INVALID;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public ResponseBoard createBoard(RequestBoard requestBoard) {
        Board board = Board.builder()
                .title(requestBoard.getTitle())
                .content(requestBoard.getContent())
                .deadline(requestBoard.getDeadline())
                .build();

        Board save = boardRepository.save(board);

        ResponseBoard responseBoard = ResponseBoard.builder()
                .id(save.getId())
                .title(save.getTitle())
                .content(save.getContent())
                .deadline(save.getDeadline())
                .createdAt(save.getCreatedAt())
                .modifiedAt(save.getModifiedAt())
                .build();

        return responseBoard;
    }

    public List<ResponseBoard> findAllBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    public ResponseBoard findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));

        ResponseBoard responseBoardById = ResponseBoard.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .deadline(board.getDeadline())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();

        return responseBoardById;
    }

    public ResponseBoard updateBoard(Long id, RequestBoard requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() ->  new BoardException(BOARD_ID_INVALID));

        board.update(requestBoard.getTitle(), requestBoard.getContent(), requestBoard.getDeadline());

        Board save = boardRepository.save(board);

        ResponseBoard responseBoardByIdUpdate = ResponseBoard.builder()
                .id(save.getId())
                .title(save.getTitle())
                .content(save.getContent())
                .deadline(save.getDeadline())
                .createdAt(save.getCreatedAt())
                .modifiedAt(save.getModifiedAt())
                .build();

        return responseBoardByIdUpdate;
    }

    public void deleteBoard(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new BoardException(BOARD_ID_INVALID));
        boardRepository.deleteById(id);
    }
}

