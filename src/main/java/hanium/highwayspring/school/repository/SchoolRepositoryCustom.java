package hanium.highwayspring.school.repository;

import hanium.highwayspring.config.res.ResponseDTO;

public interface SchoolRepositoryCustom {
    public ResponseDTO<?> findSchoolInfoWithTagsAndUserCount();
}
