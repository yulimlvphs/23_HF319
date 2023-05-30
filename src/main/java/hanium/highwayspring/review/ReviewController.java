package hanium.highwayspring.review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 등록
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Review review) {
        return ResponseEntity.ok().body(reviewService.save(review));
    }

    //해당 학교애 대한 전체 리뷰 보여주기
    @GetMapping("/list")
    public ResponseEntity list(Long schoolId) {
        return ResponseEntity.ok().body(reviewService.findAll(schoolId));
    }

    //update
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Review> update(@RequestBody ReviewDTO dto, @PathVariable Long id) {
        Optional<Review> reviewdto = this.reviewService.update(id, dto);
        return new ResponseEntity(reviewdto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
