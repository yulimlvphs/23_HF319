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
public class SchoolInfoDTO { //findSchoolInfoWithTags()에서 활용할 DTO
    private Long id;
    private String schoolName;
    private String websiteAddress;
    private List<String> tag;

    public SchoolInfoDTO(Long id, String schoolName, String websiteAddress) {
        this.id = id;
        this.schoolName = schoolName;
        this.websiteAddress = websiteAddress;
        this.tag = new ArrayList<>();
    }
}


