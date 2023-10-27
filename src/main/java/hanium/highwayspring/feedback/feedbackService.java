package hanium.highwayspring.feedback;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class feedbackService {
    @Autowired
    private feedbackRepository repository;

    // 피드백 등록
    public ResponseDTO<?> insert(FeedbackBoard feedback) {
        try {
            repository.save(feedback);
            return ResponseDTO.success(repository.findById(feedback.getId()));
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Insert Error", error);
        }
    }

    // 전체 피드백 list를 가지고 옴. (권한 필요 X)
    public ResponseDTO<List<FeedbackBoard>> getFeedbackList() {
        try {
            List<FeedbackBoard> boards = repository.findAll();
            return ResponseDTO.success(boards);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Insert Error", error);
        }
    }

    // update 기능 (권한 필요 O)
    public ResponseDTO<?> updateFeedback(feedbackDTO updatedFeedback, Long id, User user) {
        return processFeedbackRequest(id, user, feedback -> {

            feedback.setTitle(updatedFeedback.getTitle());
            feedback.setContent(updatedFeedback.getContent());
            feedback.setCategory(updatedFeedback.getCategory());
            feedback.setResponse(updatedFeedback.getResponse());

            repository.save(feedback);

            return ResponseDTO.success(feedback);
        });
    }

    // 피드백 1개 상세조회 (권한 필요 O)
    public ResponseDTO<?> getFeedback(Long id, User user) {
        return processFeedbackRequest(id, user, feedback -> ResponseDTO.success(Optional.of(feedback)));
    }

    // 피드백 삭제 (권한 필요 O)
    public ResponseDTO<?> deleteFeedback(Long id, User user) {
        return processFeedbackRequest(id, user, feedback -> {

            repository.delete(feedback);

            return ResponseDTO.success("Feedback deleted successfully.");
        });
    }

    // 사용자 권한 여부를 판단하는 메소드
    public ResponseDTO<?> processFeedbackRequest(Long id, User user, Function<FeedbackBoard, ResponseDTO<?>> action) {
        try {
            Optional<FeedbackBoard> feedbackOptional = repository.findById(id);

            return feedbackOptional.map(feedback -> {
                if (user.getId() == feedback.getUser().getId() || user.getRole() == 1) {
                    return action.apply(feedback);
                } else {
                    return ResponseDTO.fail("Unauthorized", "You are not authorized to access this feedback.");
                }
            }).orElseGet(() -> ResponseDTO.fail("Not Found", "Feedback with ID " + id + " not found."));
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Error", error);
        }
    }
}
