package hanium.highwayspring.review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity(name = "review_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //리뷰 id
    private String author; //리뷰 작성자
    private String tags; //리뷰 작성한 사람의 학과
    private String content; //리뷰 내용
    private Integer trafficRate; //별점 리뷰
    private Integer facilityRate;//별점 리뷰
    private Integer cafeteriaRate;//별점 리뷰
    private Integer educationRate;//별점 리뷰
    private Integer employmentRate;//별점 리뷰
    private Long schoolId; //학교 id

}
