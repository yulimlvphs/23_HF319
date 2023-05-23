package hanium.highwayspring.board;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.UserDTO;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    public BoardController(BoardService boardService, SchoolService schoolService, UserService userService) {
        this.boardService = boardService;
        this.schoolService = schoolService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createTodo(BoardDTO dto, HttpServletRequest request) {
        UserDTO user = userService.getUserInfo(request);
        Optional<School> school = schoolService.findBySchoolId(1L);
        Board entity = BoardDTO.toEntity(dto, school);
        entity.setId(null);
        entity.setUserId(user.getUserId());
        return boardService.create(entity);
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {

        // (1) 서비스 메서드 retrieve() 메서드를 사용해 Todo 리스트를 가져온다.
        List<Board> entities = boardService.retrieve(userId);

        // (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
        List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());

        // (3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();

        // (4) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, BoardDTO dto) {
        Optional<School> school = schoolService.findBySchoolId(1L);
        Board entity = BoardDTO.toEntity(dto, school);
        // (2) id를 userId로 초기화한다. 여기서 인증 추가
        entity.setUserId(userId);
        List<Board> entities = boardService.update(entity);
        List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());
        ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, BoardDTO dto) {
        try {
            Optional<School> school = schoolService.findBySchoolId(1L);
            Board entity = BoardDTO.toEntity(dto, school);
            entity.setUserId(userId);
            List<Board> entities = boardService.delete(entity);
            List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // (7) 혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }
}
