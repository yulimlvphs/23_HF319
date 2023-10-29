package hanium.highwayspring.board;

import hanium.highwayspring.board.DTO.BoardDTO;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.image.ImageRequestDTO;
import hanium.highwayspring.review.ReviewDTO;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping("/board")
@RestController
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final SchoolService schoolService;
    private final UserService userService;

    public BoardController(BoardService boardService, SchoolService schoolService, UserService userService) {
        this.boardService = boardService;
        this.schoolService = schoolService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseDTO<?> createBoard(@RequestBody BoardDTO dto, HttpServletRequest request) throws IOException {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 없습니다."));
        Board entity = BoardDTO.toEntity(dto, user);
        return boardService.create(entity, dto.getImageList());
    }

    @GetMapping("/detail/{boardId}")
    public ResponseDTO<?> boardDetail(HttpServletRequest request, @PathVariable("boardId") Long boardId) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 없습니다."));
        return ResponseDTO.success(boardService.getBoardDetail(user, boardId));
    }

    //cateNo = 카테고리
    //schId = 학교별 -> 학교 id, 분야별 -> 해당 분야의 id
    @GetMapping("/list/{cateNo}/{schId}")
    public ResponseDTO<?> boardList(@PathVariable(name = "cateNo") Long cateNo, @PathVariable(name = "schId") Long schId) {
        School school = schoolService.findBySchoolId(schId)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        return ResponseDTO.success(boardService.getBoardList(school.getId(), cateNo));
    }

    //cateNo = 카테고리
    //schId = 해당 메소드는 schoolId의 값이 null인 데이터를 처리
    @GetMapping("/list/{cateNo}")
    public ResponseDTO<?> boardListNoschId(@PathVariable(name = "cateNo") Long cateNo, @PathVariable(name = "schId", required = false) Long schId) {
        Long schoolId = (schId != null) ? schId : null;
        return ResponseDTO.success(boardService.getBoardList(schoolId, cateNo));
    }

    @GetMapping("/user")
    public ResponseDTO<?> userBoard(HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return ResponseDTO.success(boardService.getBoardListByUser(user.getId()));
    }

    @GetMapping("/list/heart")
    public ResponseDTO<?> boardHeartList(HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return ResponseDTO.success(boardService.getBoardHeartList(user));
    }

    @PutMapping
    public ResponseDTO<?> updateBoard(@RequestBody BoardDTO dto) {
        // 데이터베이스에서 해당 ID로 게시글 조회
        Board existingBoard = boardService.findById(dto.getId());
        // 조회 결과가 없으면 오류 발생
        if (existingBoard == null) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. ID: " + dto.getId());
        }
        return ResponseDTO.success(boardService.update(dto));
    }

    @DeleteMapping
    public ResponseDTO<?> deleteBoard(BoardDTO dto) {
        return ResponseDTO.success(boardService.delete(dto.getId()));
    }
}
