package tukorea.projectlink.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.projectlink.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
