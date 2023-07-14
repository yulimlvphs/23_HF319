package hanium.highwayspring.school.heart;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class SchoolHeartDTO {
    private Long heartId;
    private Long schoolId;
    private String schoolName; //학교 이름 (school 테이블)
    private int studentCount; //해당 학교에 재학중인 user 수 (user 테이블)
    private List<String> tag; //해당 학교에 있는 태그 리스트트 (school 테이블)

    public void setUserCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public SchoolHeartDTO(Long id, String schoolName) {
        this.schoolId = id;
        this.schoolName = schoolName;
    }
}
