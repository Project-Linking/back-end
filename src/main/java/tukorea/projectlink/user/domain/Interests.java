package tukorea.projectlink.user.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interests interests = (Interests) o;
        return interestsType == interests.interestsType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interestsType);
    }
}
