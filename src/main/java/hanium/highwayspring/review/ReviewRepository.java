package hanium.highwayspring.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review save(Review school);
    Optional<Review> findById(Long id); //해당 학교 -> 학교에 해당하는 댓글 1개를 가져와서 update에 사용
    List<Review>  findBySchoolId(Long schoolId); //학교 아이디를 가져와서 해당 학교에 대한 리뷰 전체를 보여줌

}
