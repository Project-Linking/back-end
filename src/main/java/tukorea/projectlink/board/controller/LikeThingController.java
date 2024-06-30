package tukorea.projectlink.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tukorea.projectlink.board.service.LikeThingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeThingController {

    private final LikeThingService likeThingService;

    @PostMapping("{userId}/{boardId}")
    public void likeThing(@PathVariable Long userId, @PathVariable Long boardId) {
        likeThingService.likeThing(userId, boardId);
    }

    @DeleteMapping("{userId}/{boardId}")
    public void unlikeThing(@PathVariable Long userId, @PathVariable Long boardId) {
        likeThingService.unlikeThing(userId, boardId);
    }
}
