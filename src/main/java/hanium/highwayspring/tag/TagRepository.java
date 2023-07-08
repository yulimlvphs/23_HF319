package hanium.highwayspring.tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT new hanium.highwayspring.tag.TagDTO(t.name, t.code) FROM TAG_TB t WHERE t.schoolId = :id")
    List<TagDTO> findNameAndCodeById(@Param("id") Long id);
}
