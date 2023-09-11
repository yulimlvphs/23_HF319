package hanium.highwayspring.school.Curriculum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface curriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findAllBySchoolId(Long schoolId);
}
