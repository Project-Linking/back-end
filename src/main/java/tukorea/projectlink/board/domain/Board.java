package tukorea.projectlink.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;
    @Column
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
