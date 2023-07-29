package hanium.highwayspring.school.heart.repository;

import hanium.highwayspring.config.res.ResponseDTO;

public interface schoolHeartRepositoryCustom {
    public ResponseDTO<?> findAll(Long userId);
}
