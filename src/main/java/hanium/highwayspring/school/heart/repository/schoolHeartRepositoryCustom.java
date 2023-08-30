package hanium.highwayspring.school.heart.repository;

import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface schoolHeartRepositoryCustom {
    public ResponseDTO<?> findAll(Long userId);
}

