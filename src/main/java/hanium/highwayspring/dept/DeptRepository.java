package hanium.highwayspring.dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {
    @Query("SELECT new hanium.highwayspring.dept.DeptDTO(t.name, t.description) FROM dept_tb t WHERE t.schoolId = :id")
    List<DeptDTO> findNameById(@Param("id") Long id);
}
