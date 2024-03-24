package tukorea.projectlink.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.RequestBoard;
import tukorea.projectlink.board.dto.ResponseBoard;
import tukorea.projectlink.board.respository.BoardRepository;

import java.util.List;
import java.util.NoSuchElementException;

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
                .build();

        Board save = boardRepository.save(board);

        ResponseBoard responseBoard = ResponseBoard.builder()
                .id(save.getId())
                .title(requestBoard.getTitle())
                .content(requestBoard.getContent())
                .build();

        return responseBoard;
    }

    public List<Board> findAllBoard() {
        return boardRepository.findAll();
    }

    public ResponseBoard findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("잘못된 ID 입니다."));

        ResponseBoard responseBoardById = ResponseBoard.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();

        return responseBoardById;
    }

    public Board updateBoard(Long id, RequestBoard requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("잘못된 ID 입니다."));

        board.update(requestBoard.getTitle(), requestBoard.getContent());

        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {

        boardRepository.deleteById(id);
    }
}

