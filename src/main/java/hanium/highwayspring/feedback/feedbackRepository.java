package hanium.highwayspring.feedback;
import hanium.highwayspring.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface feedbackRepository extends JpaRepository<FeedbackBoard, Long> {
}
