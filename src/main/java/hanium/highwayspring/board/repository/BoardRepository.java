package hanium.highwayspring.board.repository;

import java.util.List;
import java.util.Optional;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.DTO.BoardWithImageDTO;
import hanium.highwayspring.board.DTO.ResponseBoardDTO;
import hanium.highwayspring.board.DTO.createBoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{
	Optional<Board> findById(Long id);
	@Override
	List<BoardWithImageDTO> findBoardList(Long schId, Long cateNo);
	@Override
	Optional<ResponseBoardDTO> findBoardDetail(Long userNo, Long boardId);
	@Override
	Optional<createBoardDTO> findBoardCreate(Long boardId);
}
