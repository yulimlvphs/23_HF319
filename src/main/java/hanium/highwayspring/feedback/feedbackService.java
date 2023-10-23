package hanium.highwayspring.feedback;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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

    // 하나의 피드백 정보를 가지고 옴. (게시글 작성자 or 관리자만 볼 수 있음)
    public ResponseDTO<Optional<FeedbackBoard>> getFeedback(Long id, User user) {
        try {
            Optional<FeedbackBoard> feedbackOptional = repository.findById(id);

            if (feedbackOptional.isPresent()) {
                FeedbackBoard feedback = feedbackOptional.get();

                if (user.getId() == feedback.getUser().getId() || user.getRole() == 1) {
                    return ResponseDTO.success(feedbackOptional);
                } else {
                    return ResponseDTO.fail("Unauthorized", "You are not authorized to access this feedback.");
                }
            } else {
                return ResponseDTO.fail("Not Found", "Feedback with ID " + id + " not found.");
            }
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Error", error);
        }
    }

    // 피드백 list를 가지고 옴.
    public ResponseDTO<List<FeedbackBoard>> getFeedbackList() {
        try {
            List<FeedbackBoard> boards = repository.findAll();
            return ResponseDTO.success(boards);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Insert Error", error);
        }
    }

    // 접근 권한이 있는 사용자만 해당 글을 수정할 수 있음.
    public ResponseDTO<?> updateFeedback(feedbackDTO updatedFeedback,Long id, User user) {
        try {
            Optional<FeedbackBoard> feedbackOptional = repository.findById(id);

            if (feedbackOptional.isPresent()) {
                FeedbackBoard feedback = feedbackOptional.get();

                // 사용자의 ID와 피드백 게시물의 사용자 ID가 일치하거나 사용자의 역할이 1인 경우에만 수정 허용
                if (user.getId() == feedback.getUser().getId() || user.getRole() == 1) {
                    // 엔티티 필드 업데이트
                    feedback.setTitle(updatedFeedback.getTitle());
                    feedback.setContent(updatedFeedback.getContent());
                    feedback.setCategory(updatedFeedback.getCategory());
                    feedback.setResponse(updatedFeedback.getResponse());

                    // 변경 사항을 데이터베이스에 저장
                    repository.save(feedback);

                    return ResponseDTO.success(feedback);
                } else {
                    return ResponseDTO.fail("Unauthorized", "You are not authorized to update this feedback.");
                }
            } else {
                return ResponseDTO.fail("Not Found", "Feedback not found.");
            }
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Error", error);
        }
    }

    public ResponseDTO<?> deleteFeedback(Long id, User user) {
        try {
            Optional<FeedbackBoard> feedbackOptional = repository.findById(id);

            if (feedbackOptional.isPresent()) {
                FeedbackBoard feedback = feedbackOptional.get();

                // 사용자의 ID와 피드백 게시물의 사용자 ID가 일치하거나 사용자의 역할이 1인 경우에만 삭제 허용
                if (user.getId() == feedback.getUser().getId() || user.getRole() == 1) {
                    // 피드백 게시물 삭제
                    repository.delete(feedback);

                    return ResponseDTO.success("Feedback deleted successfully.");
                } else {
                    return ResponseDTO.fail("Unauthorized", "You are not authorized to delete this feedback.");
                }
            } else {
                return ResponseDTO.fail("Not Found", "Feedback not found.");
            }
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Error", error);
        }
    }

}
