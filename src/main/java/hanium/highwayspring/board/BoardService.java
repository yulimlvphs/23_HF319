package hanium.highwayspring.board;

import java.util.List;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    // insert
    public ResponseEntity create(final Board entity) {
        try {
            validate(entity);
            log.info("Entity Id : {}  is saved.", entity.getId());
            boardRepository.save(entity);
            return ResponseEntity.ok().body(boardRepository.findById(entity.getId()));
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.fail("Error", error);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // select
    public List<Board> retrieve(final Long userNo) {
        log.info("Entity userId : {} is find.", userNo);
        List<Board> boards = boardRepository.findByUserId(userNo);
        return boardRepository.findByUserId(userNo);
    }

    public List<Board> boardList(Long schoolNo) {
        List<Board> boards = boardRepository.findBySchoolId(schoolNo);
        return boards;
    }

    // update
    @Transactional
    public List<Board> update(final BoardDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        validate(board);
        board.updateBoard(dto);
        return retrieve(board.getUser().getId());
    }

    // delete
    public List<Board> delete(Long boardId) {
        Board board = findById(boardId);
        validate(board);
        try {
            boardRepository.delete(board);
        } catch (Exception e) {
            log.error("error deleting entity ", board.getId(), e);
            throw new RuntimeException("error deleteing entity " + board.getId());
        }
        return retrieve(board.getUser().getId());
    }

    // 리팩토링하나 메서드
    private void validate(final Board entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("entity cannot be null.");
        }

        if (entity.getUser().getUid() == null) {
            log.warn("Unkown user.");
            throw new RuntimeException("Unknown user");
        }
    }

    public Board findById(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }
}
 