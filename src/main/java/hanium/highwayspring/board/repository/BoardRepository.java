package hanium.highwayspring.board.repository;

import java.util.List;
import java.util.Optional;

import hanium.highwayspring.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{
	List<Board> findByUserId(Long userNo);
	List<Board> findBySchoolId(Long schoolId);
	Optional<Board> findById(Long id);
}
