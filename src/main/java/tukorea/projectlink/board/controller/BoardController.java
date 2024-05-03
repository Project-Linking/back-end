package tukorea.projectlink.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.board.dto.RequestBoard;
import tukorea.projectlink.board.dto.ResponseBoard;
import tukorea.projectlink.board.service.BoardService;
import tukorea.projectlink.global.common.CommonResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public CommonResponse<ResponseBoard> createPost(@Valid @RequestBody RequestBoard requestBoard){
        return CommonResponse.successWithData(boardService.createBoard(requestBoard));
    }

    @GetMapping
    public CommonResponse<List<ResponseBoard>> findAllBoard(){
        return CommonResponse.successWithData(boardService.findAllBoard());
    }

    @GetMapping("/{id}")
    public CommonResponse<ResponseBoard> findBoardById(@PathVariable Long id){
        return CommonResponse.successWithData(boardService.findBoardById(id));
    }

    @PutMapping("/{id}")
    public CommonResponse<ResponseBoard> updateBoard(@PathVariable Long id, @Valid @RequestBody RequestBoard requestBoard){
        return CommonResponse.successWithData(boardService.updateBoard(id, requestBoard));
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return CommonResponse.successWithEmptyData();
    }


}
