package tukorea.projectlink.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tukorea.projectlink.board.enums.Category;
import tukorea.projectlink.comment.domain.Comment;
import tukorea.projectlink.user.domain.User;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board extends Timestamp {

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime deadline;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "board_category", joinColumns = @JoinColumn(name = "board_id"))
    private Set<Category> category = new HashSet<>();

    @Column
    private Long likeNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    public void update(String title, String content, LocalDateTime deadline, Set<Category> category) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.category = category;
    }

    public void LikeIncrease(Long id){
        this.likeNum += 1;
    }

    public void LikeDecrease(Long id){
        this.likeNum -= 1;
    }
}
