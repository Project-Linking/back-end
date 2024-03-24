package tukorea.projectlink.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.RequestBoard;
import tukorea.projectlink.board.dto.ResponseBoard;
import tukorea.projectlink.board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseBoard createPost(@RequestBody RequestBoard requestBoard){
        return boardService.createBoard(requestBoard);
    }

    @GetMapping
    public List<Board> findAllBoard(){
        return boardService.findAllBoard();
    }

    @GetMapping("/{id}")
    public ResponseBoard findBoardById(@PathVariable Long id){
        return boardService.findBoardById(id);
    }

    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody RequestBoard requestBoard){
        return boardService.updateBoard(id, requestBoard);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
    }


}
