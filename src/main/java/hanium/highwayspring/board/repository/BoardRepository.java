package hanium.highwayspring.board.repository;

import java.util.List;
import java.util.Optional;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.ResponseBoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{
	List<Board> findByUserId(Long userNo);
	List<Board> findBySchoolId(Long schoolId);
	Optional<Board> findById(Long id);
	@Override
	List<Board> findBoardList(Long schId, Long cateNo);
	@Override
	Optional<ResponseBoardDTO> findBoardDetail(Long userNo, Long boardId);
}
