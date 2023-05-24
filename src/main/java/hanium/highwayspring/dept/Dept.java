package hanium.highwayspring.dept;

import hanium.highwayspring.school.School;
import hanium.highwayspring.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "DEPT_TB")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
