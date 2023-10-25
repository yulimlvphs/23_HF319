package hanium.highwayspring.review;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final SchoolService schoolService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, SchoolService schoolService, UserService userService) {
        this.reviewService = reviewService;
        this.schoolService = schoolService;
        this.userService = userService;
    }

    // 리뷰 등록
    @PostMapping()
    public ResponseDTO<?> save(@RequestBody ReviewDTO reviewdto, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        // 유저 데이터에 있는 schoolId값과 넘어온 schoolId의 값이 같지 않으면 리뷰를 작성할 수 없음.
        if(user.getSchoolId().getId() != reviewdto.getSchoolId()){
            return ResponseDTO.fail("false","false");
        }
        else {
            Review review =  ReviewDTO.toEntity(reviewdto, user);
            return reviewService.save(review);
        }
    }

    //해당 학교애 대한 전체 리뷰 보여주기
    @GetMapping("/list/{id}")
    public ResponseDTO<?> getReviewsBySchoolId(@PathVariable(name = "id") Long schoolId) {
        School school = schoolService.findBySchoolId(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        return reviewService.findAll(school);
    }

    //update
    @PutMapping(value = "/{id}")
    public ResponseDTO<?> update(@RequestBody ReviewDTO dto, @PathVariable Long id) {
        return reviewService.update(id, dto);
    }

    //delete
    @PostMapping(value = "/{id}")
    public ResponseDTO<?> delete(@PathVariable Long id) {
        return reviewService.softDelete(id);
    }
}
