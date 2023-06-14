package hanium.highwayspring.review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

@Entity(name = "review_TB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate // 변경 필드만 반영되도록 함.
public class Review{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //리뷰 id
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User userId;//리뷰 작성자
    private String tags; //리뷰 작성한 사람의 학과
    private String content; //리뷰 내용
    private Integer trafficRate; //별점 리뷰
    private Integer facilityRate;//별점 리뷰
    private Integer cafeteriaRate;//별점 리뷰
    private Integer educationRate;//별점 리뷰
    private Integer employmentRate;//별점 리뷰
    @ManyToOne
    @JoinColumn(name = "schoolId")
    private School schoolId; //학교 id

}
