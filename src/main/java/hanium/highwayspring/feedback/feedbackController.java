package hanium.highwayspring.feedback;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/feedback")
public class feedbackController {

    private final UserService userService;
    private final feedbackService feedbackservice;

    public feedbackController(UserService userService, feedbackService feedbackservice) {
        this.userService = userService;
        this.feedbackservice = feedbackservice;
    }

    // 피드백 등록
    @PostMapping
    public ResponseDTO<?> insertFeedback(@RequestBody feedbackDTO dto, HttpServletRequest request) throws IOException {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        FeedbackBoard entity = feedbackDTO.toEntity(dto, user);
        entity.setCreateDate(LocalDateTime.now());
        return feedbackservice.insert(entity);
    }

    // 피드백 게시글 상세조회
    @GetMapping("/{id}")
    public ResponseDTO<?> getFeedback(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 업습니다."));
        return feedbackservice.getFeedback(id, user);
    }

    // 피드백 게시글 전체조회 (list)
    @GetMapping("/list")
    public ResponseDTO<?> getFeedbackList() {
        return feedbackservice.getFeedbackList();
    }

    // 피드백 수정
    @PutMapping("/{id}")
    public ResponseDTO<?> updateFeedback(@RequestBody feedbackDTO updatedFeedback, @PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return feedbackservice.updateFeedback(updatedFeedback, id, user);
    }

    // 피드백 삭제
    @DeleteMapping("/{id}")
    public ResponseDTO<?> deleteFeedback(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return feedbackservice.deleteFeedback(id, user);
    }
}
