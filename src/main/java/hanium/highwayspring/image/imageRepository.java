package hanium.highwayspring.image;

import hanium.highwayspring.school.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface imageRepository extends JpaRepository<Image, Long>, imageRepositoryCustom {
    List<Image> findAllByBoardId(Long boardId);
    void deleteAllByBoardId(Long boardId);
    @Override
    void deleteImageForUpdate(Long boardId, String urlValue);
}
