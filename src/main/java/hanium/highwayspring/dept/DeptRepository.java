package hanium.highwayspring.dept;
import hanium.highwayspring.tag.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DeptRepository extends JpaRepository<Dept, Long> {
    @Query("SELECT new hanium.highwayspring.dept.DeptDTO(t.name) FROM DEPT_TB t WHERE t.schoolId = :id")
    List<DeptDTO> findNameById(@Param("id") Long id);
}
