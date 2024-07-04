package tukorea.projectlink.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.projectlink.group.domain.Group;
import tukorea.projectlink.group.dto.CreateGroupRequest;
import tukorea.projectlink.group.repository.GroupRepository;
import tukorea.projectlink.group.service.mapper.GroupMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private GroupRepository groupRepository;
    private GroupMapper groupMapper;

    public void create(CreateGroupRequest createGroupRequest) {
        Group group = groupMapper.mapFrom(createGroupRequest);
        groupRepository.save(group);
    }
}
