package hanium.highwayspring.school.heart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "schoolHeart_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "schoolId")
    @JsonIgnore
    private School school;
}
