package tukorea.projectlink.user.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Interests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERESTS_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private InterestsType interestsType;

    public Interests(InterestsType interestsType) {
        this.interestsType = interestsType;
    }
}
