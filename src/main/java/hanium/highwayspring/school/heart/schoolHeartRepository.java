package hanium.highwayspring.school.heart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface schoolHeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findByUserId(Long heart);
    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);
    long countByUserId(Long userId);
}
