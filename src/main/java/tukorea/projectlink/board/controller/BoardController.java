package tukorea.projectlink.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.auth.Auth;
import tukorea.projectlink.auth.Authentication;
import tukorea.projectlink.board.dto.BoardMainResponse;
import tukorea.projectlink.board.dto.BoardRequest;
import tukorea.projectlink.board.dto.BoardDetailsResponse;
import tukorea.projectlink.board.enums.Category;
import tukorea.projectlink.board.service.BoardService;
import tukorea.projectlink.global.common.CommonResponse;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public CommonResponse<BoardDetailsResponse> createPost(@Valid @RequestBody BoardRequest boardRequest,
                                                           @Auth Authentication auth){
        return CommonResponse.successWithData(boardService.createBoard(boardRequest,auth));
    }

    @GetMapping
    public CommonResponse<Page<BoardMainResponse>> findAllBoard(@PageableDefault(size = 5) Pageable pageable){
        return CommonResponse.successWithData(boardService.findAllBoard(pageable));
    }

    @GetMapping("/search")
    public CommonResponse<Page<BoardMainResponse>> findBoardBySomething(@RequestParam(value = "title", required = false) String title,
                                                                        @RequestParam(value = "nickname", required = false) String nickname,
                                                                        @RequestParam(value = "category", required = false) Set<Category> category,
                                                                        @PageableDefault(size = 5) Pageable pageable){
        return CommonResponse.successWithData(boardService.findBoardBySomething(title, nickname, category, pageable));
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
