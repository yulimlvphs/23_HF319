package hanium.highwayspring.review;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final SchoolService schoolService;

    public ReviewController(ReviewService reviewService, SchoolService schoolService) {
        this.reviewService = reviewService;
        this.schoolService = schoolService;
    }

    // 리뷰 등록
    @PostMapping()
    public ResponseEntity<Review> save(@RequestBody ReviewDTO reviewdto) {
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        Review review =  ReviewDTO.toEntity(reviewdto, school);
        return ResponseEntity.ok().body(reviewService.save(review));
    }

    //해당 학교애 대한 전체 리뷰 보여주기
    @GetMapping()
    public ResponseEntity<List<Review>> getReviewsBySchoolId(@RequestParam(name = "schoolId") Long schoolId) {
        School school = schoolService.findBySchoolId(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        List<Review> reviews = reviewService.findAll(school);
        return ResponseEntity.ok(reviews);
    }
    //update
    @PutMapping(value = "/{id}")
    public ResponseEntity<Review> update(@RequestBody ReviewDTO dto, @PathVariable Long id) {
        Optional<Review> reviewdto = this.reviewService.update(id, dto);
        return new ResponseEntity(reviewdto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
