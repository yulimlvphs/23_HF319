package hanium.highwayspring.school.heart;
import hanium.highwayspring.tag.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface schoolHeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findAllByUserId(Long userId);
    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);
    long countByUserId(Long userId);
}
