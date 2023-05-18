package hanium.highwayspring.school;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    School save(School school);
    Optional<School> findById(Long sch_id);
    List<School> findAll();
}
