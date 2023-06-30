package hanium.highwayspring.school.heart;
import hanium.highwayspring.review.Review;
import hanium.highwayspring.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findByUserId(Long heart);
    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);
    long countByUserId(Long userId);
}
