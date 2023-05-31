package hanium.highwayspring.board;

import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.UserDTO;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> createBoard(BoardDTO dto, HttpServletRequest request) {
        UserDTO user = userService.getUserInfo(request);
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        Board entity = BoardDTO.toEntity(dto, school);
        entity.setUserId(user.getUserId());
        return boardService.create(entity);
    }

    @GetMapping
    public ResponseEntity<?> retrieveBoardList(HttpServletRequest request) {
        UserDTO user = userService.getUserInfo(request);
        return ResponseEntity.ok().body(boardService.retrieve(user.getUserId()));
    }

    @PutMapping
    public ResponseEntity<?> updateBoard(BoardDTO dto) {
        return ResponseEntity.ok().body(boardService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBoard(BoardDTO dto, HttpServletRequest request) {
        UserDTO user = userService.getUserInfo(request);
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        Board entity = BoardDTO.toEntity(dto, school);
        entity.setUserId(user.getUserId());
        return ResponseEntity.ok().body(boardService.delete(entity));
    }
}
