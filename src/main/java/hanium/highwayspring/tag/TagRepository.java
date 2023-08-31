package hanium.highwayspring.tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT new hanium.highwayspring.tag.TagDTO(t.name, t.code) FROM tag_tb t WHERE t.schoolId = :id")
    List<TagDTO> findNameAndCodeById(@Param("id") Long id);
}