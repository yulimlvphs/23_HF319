package hanium.highwayspring.feedback;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
public class feedbackController {

    private final UserService userService;
    private final feedbackService feedbackservice;

    public feedbackController(UserService userService, feedbackService feedbackservice) {
        this.userService = userService;
        this.feedbackservice = feedbackservice;
    }

    @PostMapping
    public ResponseDTO<?> insertFeedback(@RequestBody feedbackDTO dto, HttpServletRequest request) throws IOException {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        FeedbackBoard entity = feedbackDTO.toEntity(dto, user);
        entity.setCreateDate(LocalDateTime.now());
        return feedbackservice.insert(entity);
    }

    @GetMapping("/{id}")
        public ResponseDTO<?> getFeedback(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        ResponseDTO<Optional<FeedbackBoard>> feedback = feedbackservice.getFeedback(id, user);
        return feedback;
    }

    @GetMapping("/list")
    public ResponseDTO<?> getFeedbackList() {
        ResponseDTO<List<FeedbackBoard>> feedbackList = feedbackservice.getFeedbackList();
        return feedbackList;
    }

    @PutMapping("/{id}")
    public ResponseDTO<?> updateFeedback(@RequestBody feedbackDTO updatedFeedback, @PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return feedbackservice.updateFeedback(updatedFeedback, id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<?> deleteFeedback(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return feedbackservice.deleteFeedback(id, user);
    }
}
