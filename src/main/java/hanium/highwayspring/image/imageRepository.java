package hanium.highwayspring.image;

import hanium.highwayspring.school.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface imageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByBoardId(Long id);
}
