package hanium.highwayspring.tag;

import hanium.highwayspring.school.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity(name = "tag_tb")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 태크 entity (ex) it, 농업..
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long schoolId;
    private String name;
    private Long code;
}
