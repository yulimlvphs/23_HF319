package hanium.highwayspring.board.repository;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.DTO.BoardWithImageDTO;
import hanium.highwayspring.board.DTO.ResponseBoardDTO;
import hanium.highwayspring.board.DTO.createBoardDTO;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    List<BoardWithImageDTO> findAllBoardList(Long schId, Long cateNo);
    Optional<ResponseBoardDTO> findBoardDetail(Long uId, Long boardId);
    List<Board> findBoardHeartList(Long uId);
    Optional<createBoardDTO> findBoardCreate(Long boardId);
    List<BoardWithImageDTO> findAllBoardByUserId(Long userId);
}