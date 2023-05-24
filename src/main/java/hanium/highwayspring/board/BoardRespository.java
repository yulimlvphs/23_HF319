package hanium.highwayspring.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRespository extends JpaRepository<Board, Long>{
	// ?1은 메서드의 매개변수의 순서 위치다.
	//@Query("select id, userId, title, done from Todo t where t.userId = ?1")
	List<Board> findByUserId(String userId);
	List<Board> findBySchoolId(Long schoolId);
	Optional<Board> findById(Long id);
}
