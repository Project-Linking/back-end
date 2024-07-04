package tukorea.projectlink.group.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea.projectlink.global.common.CommonResponse;
import tukorea.projectlink.group.dto.CreateGroupRequest;
import tukorea.projectlink.group.service.GroupService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public CommonResponse<?> create(
            @RequestBody @Valid CreateGroupRequest createGroupRequest
    ) {
        groupService.create(createGroupRequest);
        return CommonResponse.successWithEmptyData();
    }
}
