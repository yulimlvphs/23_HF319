package hanium.highwayspring.school.heart.DTO;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@Data
@Getter
@Setter
public class SchoolHeartDTO {
    private Long heartId;
    private Long schoolId;
    private String schoolName; //학교 이름 (school 테이블)
    private int studentCount; //해당 학교에 재학중인 user 수 (user 테이블)
    private String schoolImage; //학교 로고 이미지
    private List<String> tag; //해당 학교에 있는 태그 리스트트 (school 테이블)

    public void setUserCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public SchoolHeartDTO(Long id, Long schoolId, String schoolName, int studentCount, String schoolImage, List<String> tag) {
        this.heartId = id;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.studentCount = studentCount;
        this.schoolImage = schoolImage;
        this.tag = tag;
    }
}