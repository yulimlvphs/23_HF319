package hanium.highwayspring.board;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseDTO<?> createBoard(BoardDTO dto, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        Board entity = BoardDTO.toEntity(dto, school, user);
        return ResponseDTO.success(boardService.create(entity));
    }

    @GetMapping("/detail/{boardId}")
    public ResponseDTO<?> boardDetail(HttpServletRequest request, @PathVariable("boardId") Long boardId) {
        User user = userService.getUser(request)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보가 업습니다."));
        return ResponseDTO.success(boardService.getBoardDetail(user, boardId));
    }

    @GetMapping("/list/{schId}")
    public ResponseDTO<?> boardList(@PathVariable("schId") Long schId, @RequestParam(name = "cateNo") Long cateNo) {
        School school = schoolService.findBySchoolId(schId)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        return ResponseDTO.success(boardService.getBoardList(school.getId(), cateNo));
    }

    @PutMapping
    public ResponseDTO<?> updateBoard(BoardDTO dto) {
        return ResponseDTO.success((boardService.update(dto)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBoard(BoardDTO dto) {
        return ResponseEntity.ok().body(boardService.delete(dto.getId()));
    }
}
