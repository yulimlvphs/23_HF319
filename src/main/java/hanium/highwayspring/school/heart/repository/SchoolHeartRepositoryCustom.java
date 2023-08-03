package hanium.highwayspring.school.heart.repository;

import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface SchoolHeartRepositoryCustom {
    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);
}
