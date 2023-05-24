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
    private final BoardRespository boardRespository;

    // insert
    public ResponseEntity create(final Board entity) {
        try {
            validate(entity);
            log.info("Entity Id : {}  is saved.", entity.getId());
            boardRespository.save(entity);
            return ResponseEntity.ok().body(boardRespository.findById(entity.getId()));
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // select
    public List<Board> retrieve(final String userId) {
        log.info("Entity userId : {} is find.", userId);
        return boardRespository.findByUserId(userId);
    }

    // update
    @Transactional
    public List<Board> update(final BoardDTO dto) {
        Board board = boardRespository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        validate(board);
        board.updateBoard(dto);
        return retrieve(board.getUserId());
    }

    // delete
    public List<Board> delete(final Board entity) {
        validate(entity);
        try {
            boardRespository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity ", entity.getId(), e);
            throw new RuntimeException("error deleteing entity " + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    // 리팩토링하나 메서드
    private void validate(final Board entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("entity cannot be null.");
        }

        if (entity.getUserId() == null) {
            log.warn("Unkown user.");
            throw new RuntimeException("Unknown user");
        }
    }
}
 