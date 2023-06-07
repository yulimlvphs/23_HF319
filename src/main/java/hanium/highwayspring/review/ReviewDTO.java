package hanium.highwayspring.review;

import hanium.highwayspring.school.School;
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
    private String author;
    private String tags;
    private String content;
    private Integer trafficRate;
    private Integer facilityRate;
    private Integer cafeteriaRate;
    private Integer educationRate;
    private Integer employmentRate;
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

    public static Review toEntity(final ReviewDTO dto, final School school) {
        return Review.builder()
                .id(dto.getId())
                .author(dto.getAuthor())
                .tags(dto.getTags())
                .content(dto.getContent())
                .trafficRate(dto.getTrafficRate())
                .facilityRate(dto.getFacilityRate())
                .cafeteriaRate(dto.getCafeteriaRate())
                .educationRate(dto.getEducationRate())
                .employmentRate(dto.getEmploymentRate())
                .schoolId(school)
                .build();
    }
}
