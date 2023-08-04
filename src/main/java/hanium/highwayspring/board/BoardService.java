package hanium.highwayspring.board;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.Tuple;
import hanium.highwayspring.board.repository.BoardRepository;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.image.imageService;
import hanium.highwayspring.user.User;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final imageService imageService;

    // insert
    public ResponseDTO<?> create(final Board entity, final List<MultipartFile> imageList) {
        try {
            validate(entity);
            boardRepository.save(entity); //이미지를 뺀 나머지 컬럼 저장
            imageService.upload(imageList, entity.getId()); //이미지 저장 코드
            return ResponseDTO.success(boardRepository.findById(entity.getId()));
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Error", error);
        }
    }

    // select
    public Optional<ResponseBoardDTO> getBoardDetail(final User user, Long boardId) {
        Optional<ResponseBoardDTO> boardDetail = boardRepository.findBoardDetail(user.getId(), boardId);
        return boardDetail;
    }

    public List<BoardWithImageDTO> getBoardList(Long schId, Long cateNo) {
        List<BoardWithImageDTO> boards = boardRepository.findBoardList(schId, cateNo);
        return boards;
    }

    public List<Board> getBoardHeartList(final User user) {
        List<Board> boards = boardRepository.findBoardHeartList(user.getId());
        return boards;
    }

    // update
    /*@Transactional
    public List<Board> update(final BoardDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        validate(board);
        board.updateBoard(dto);
        return getBoardList(board.getSchool().getId(), board.getCategory());
    }*/

    // delete
    /*public List<Board> delete(Long boardId) {
        Board board = findById(boardId);
        validate(board);
        try {
            boardRepository.delete(board);
        } catch (Exception e) {
            log.error("error deleting entity ", board.getId(), e);
            throw new RuntimeException("error deleteing entity " + board.getId());
        }
        return getBoardList(board.getSchool().getId(), board.getCategory());
    }*/

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
 