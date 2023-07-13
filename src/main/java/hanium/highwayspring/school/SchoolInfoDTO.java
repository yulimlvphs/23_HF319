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
public class SchoolInfoDTO { //findSchoolInfoWithTagsAndUserCount()에서 활용할 DTO
    private Long id; //(school 테이블)
    private String schoolName; //학교 이름 (school 테이블)
    private int studentCount; //해당 학교에 재학중인 user 수 (user 테이블)
    private List<String> tag; //해당 학교에 있는 태그 리스트트 (tag 테이블)
    public void setUserCount(int studentCount) {
        this.studentCount = studentCount;
    }
}


