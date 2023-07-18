package hanium.highwayspring.school.repository;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.repository.SchoolRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolRepositoryCustom {
    School save(School school);
    Optional<School> findById(Long id);
    List<School> findAll();
}
