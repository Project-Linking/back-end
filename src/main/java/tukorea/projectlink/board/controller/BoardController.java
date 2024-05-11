package tukorea.projectlink.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
<<<<<<< Updated upstream
import tukorea.projectlink.board.dto.BoardMainResponse;
import tukorea.projectlink.board.dto.BoardRequest;
import tukorea.projectlink.board.dto.BoardDetailsResponse;
=======
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.board.dto.RequestBoard;
import tukorea.projectlink.board.dto.ResponseBoard;
>>>>>>> Stashed changes
import tukorea.projectlink.board.service.BoardService;
import tukorea.projectlink.global.common.CommonResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public CommonResponse<BoardDetailsResponse> createPost(@Valid @RequestBody BoardRequest boardRequest, @Auth Authentication auth){
        return CommonResponse.successWithData(boardService.createBoard(boardRequest,auth));
    }

    @GetMapping
<<<<<<< Updated upstream
    public CommonResponse<List<BoardMainResponse>> findAllBoard(){
=======
    public CommonResponse<List<Board>> findAllBoard(){
>>>>>>> Stashed changes
        return CommonResponse.successWithData(boardService.findAllBoard());
    }

    @GetMapping("/{id}")
    public CommonResponse<BoardDetailsResponse> findBoardById(@PathVariable Long id){
        return CommonResponse.successWithData(boardService.findBoardById(id));
    }

    @PutMapping("/{id}")
    public CommonResponse<BoardDetailsResponse> updateBoard(@PathVariable Long id, @Valid @RequestBody BoardRequest boardRequest){
        return CommonResponse.successWithData(boardService.updateBoard(id, boardRequest));
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return CommonResponse.successWithEmptyData();
    }


}
