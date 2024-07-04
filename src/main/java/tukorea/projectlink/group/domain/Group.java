package tukorea.projectlink.group.domain;

import jakarta.persistence.*;
import lombok.Builder;
import tukorea.projectlink.board.domain.Board;
import tukorea.projectlink.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<User> users = new ArrayList<>();

    @OneToOne
    private Board board;
    private String groupName;
    private Long maxUsers;

    @Builder
    public Group(final List<User> users, final Board board, final String groupName, final Long maxUsers) {
        this.users = users;
        this.board = board;
        this.groupName = groupName;
        this.maxUsers = maxUsers;
    }
}
