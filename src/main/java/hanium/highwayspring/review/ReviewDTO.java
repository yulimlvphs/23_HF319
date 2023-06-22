package hanium.highwayspring.review;

import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDTO {
    private Long id;
    private String userId;
    private String userName;
    private Long role;
    private String tags;
    private String content;
    private Integer trafficRate;
    private Integer facilityRate;
    private Integer cafeteriaRate;
    private Integer educationRate;
    private Integer employmentRate;
    /*private boolean isDeleted;*/
    private Long schoolId;

    /*public ReviewDTO(final Review entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.tags = entity.getTags();
        this.content = entity.getContent();
        this.trafficRate = entity.getTrafficRate();
        this.facilityRate = entity.getFacilityRate();
        this.cafeteriaRate = entity.getCafeteriaRate();
        this.educationRate = entity.getEducationRate();
        this.employmentRate = entity.getEmploymentRate();
        this.schoolId = entity.getSchoolId();
    } */

    public static Review toEntity(final ReviewDTO dto, final User user) {
        School school = new School(dto.getSchoolId());
        return Review.builder()
                .userId(user)
                .userName(user.getUid())
                .role(user.getRole())
                .tags(dto.getTags())
                .content(dto.getContent())
                .trafficRate(dto.getTrafficRate())
                .facilityRate(dto.getFacilityRate())
                .cafeteriaRate(dto.getCafeteriaRate())
                .educationRate(dto.getEducationRate())
                .employmentRate(dto.getEmploymentRate())
               /* .isDeleted(dto.isDeleted())*/
                .schoolId(school)
                .build();
    }
}
