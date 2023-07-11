package hanium.highwayspring.school;
import hanium.highwayspring.tag.TagDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class SchoolInfoDTO { //findSchoolInfoWithTags()에서 활용할 DTO
    private Long id;
    private String schoolName;
    private int studentCount;
    private List<String> tag;

    public SchoolInfoDTO(Long id, String schoolName) {
        this.id = id;
        this.schoolName = schoolName;
        this.tag = new ArrayList<>();
    }

    public void setUserCount(int studentCount) {
        this.studentCount = studentCount;
    }
}


