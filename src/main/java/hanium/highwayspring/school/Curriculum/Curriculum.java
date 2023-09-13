package hanium.highwayspring.school.Curriculum;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "curriculum_tb")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "depart")
    private String depart;

    @Column(name = "grade")
    private Long grade;

    @Column(name = "content")
    private String content;
}
