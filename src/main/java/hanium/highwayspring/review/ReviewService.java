package hanium.highwayspring.review;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {
    public Review save(Review school);

    public Optional<Review> findById(Long id); //해당 학교 -> 학교에 해당하는 댓글 1개를 가져와서 update에 사용

    public Optional<Review> update(Long idx, ReviewDTO dto);

    public List<Review> findAll(Long schoolId); //학교 아이디를 가져와서 해당 학교에 대한 리뷰 전체를 보여줌

    public void deleteReview(Long id);
}
