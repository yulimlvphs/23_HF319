package hanium.highwayspring.board;

import java.util.List;
import java.util.Optional;

import hanium.highwayspring.board.DTO.BoardDTO;
import hanium.highwayspring.board.DTO.BoardWithImageDTO;
import hanium.highwayspring.board.DTO.ResponseBoardDTO;
import hanium.highwayspring.board.repository.BoardRepository;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.image.imageService;
import hanium.highwayspring.user.User;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final imageService imageService;

    // insert
    public ResponseDTO<?> create(final Board entity, final List<String> imageUrls) {
        try {
            validate(entity);
            boardRepository.save(entity); //이미지를 뺀 나머지 컬럼 저장
            imageService.moveImagesToFinalLocation(imageUrls, entity.getId());
            return ResponseDTO.success(boardRepository.findBoardCreate(entity.getId()));
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseDTO.fail("Error", error);
        }
    }

    // 게시글 상세보기
    public Optional<ResponseBoardDTO> getBoardDetail(final User user, Long boardId) {
        Optional<ResponseBoardDTO> boardDetail = boardRepository.findBoardDetail(user.getId(), boardId);
        return boardDetail;
    }

    // 게시글 전체보기
    public List<BoardWithImageDTO> getBoardList(Long schId, Long cateNo) {
        List<BoardWithImageDTO> boards = boardRepository.findAllBoardList(schId, cateNo);
        return boards;
    }


    //좋아요 누른 학교 보여주기
    public List<Board> getBoardHeartList(final User user) {
        List<Board> boards = boardRepository.findBoardHeartList(user.getId());
        return boards;
    }

    //사용자가 작성한 게시글 list 보여주기
    public List<BoardWithImageDTO> getBoardListByUser(Long id){
        return boardRepository.findAllBoardByUserId(id);
    }

    // update
    @Transactional
    public List<BoardWithImageDTO> update(final BoardDTO dto) {
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        validate(board);
        board.updateBoard(dto);
        imageService.updateImages(dto.getImageList(), dto.getId());
        return getBoardList(board.getSchool().getId(), board.getCategory());
    }

    // delete
    public List<BoardWithImageDTO> delete(Long boardId) {
        Board board = findById(boardId);
        try {
            imageService.deleteImages(boardId);
            boardRepository.delete(board);
        } catch (Exception e) {
            log.error("error deleting entity ", board.getId(), e);
            throw new RuntimeException("error deleteing entity " + board.getId());
        }
        return boardRepository.findAllBoardList(board.getSchool().getId(), board.getCategory());
    }

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
 