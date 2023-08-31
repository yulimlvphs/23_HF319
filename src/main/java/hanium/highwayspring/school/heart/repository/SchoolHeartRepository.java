package hanium.highwayspring.school.heart.repository;
import hanium.highwayspring.school.heart.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolHeartRepository extends JpaRepository<Heart, Long>, schoolHeartRepositoryCustom {
    List<Heart> findAllByUserId(Long userId);
    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);
    long countByUserId(Long userId);
}
