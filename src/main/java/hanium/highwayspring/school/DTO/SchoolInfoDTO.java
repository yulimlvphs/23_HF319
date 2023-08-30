package hanium.highwayspring.school.DTO;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import hanium.highwayspring.tag.TagDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@Data
@Setter
@Getter
public class SchoolInfoDTO { //findSchoolInfoWithTagsAndUserCount()에서 활용할 DTO
    private Long schoolId; //(school 테이블)
    private String schoolName; //학교 이름 (school 테이블)
    private Integer studentCount; //해당 학교에 재학중인 user 수 (user 테이블)
    private String schoolImage;
    private List<String> tag; //해당 학교에 있는 태그 리스트트 (school 테이블)

    public SchoolInfoDTO(Long id, String schoolName, Integer studentCount, String schoolImage, List<String> tag) {
        this.schoolId = id;
        this.schoolName = schoolName;
        this.studentCount = studentCount;
        this.schoolImage = schoolImage;
        this.tag = tag;
    }

}


