package hanium.highwayspring.board;

import java.util.List;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

//@Service
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
    public List<Board> retrieve(final String userId) {
        log.info("Entity userId : {} is find.", userId);
        List<Board> boards = boardRepository.findByUserId(userId);

        return boardRepository.findByUserId(userId);
//        return boardRespository.findBySchoolId(1L);
    }

    // update
    @Transactional
    public List<Board> update(final BoardDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        validate(board);
        board.updateBoard(dto);
        return retrieve(board.getUser().getUid());
    }

    // delete
    public List<Board> delete(final Board entity) {
        validate(entity);
        try {
            boardRepository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity ", entity.getId(), e);
            throw new RuntimeException("error deleteing entity " + entity.getId());
        }
        return retrieve(entity.getUser().getUid());
    }

    // 리팩토링하나 메서드
    private void validate(final Board entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("entity cannot be null.");
        }

        if (entity.getUser() == null) {
            log.warn("Unkown user.");
            throw new RuntimeException("Unknown user");
        }
    }

    public Board findById(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }
}
 