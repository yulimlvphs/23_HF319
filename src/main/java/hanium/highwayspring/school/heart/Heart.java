package hanium.highwayspring.school.heart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity(name = "schoolheart_tb")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School school;
}
