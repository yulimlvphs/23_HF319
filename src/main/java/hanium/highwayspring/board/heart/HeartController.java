package hanium.highwayspring.board.heart;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.BoardService;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/heart")
@RestController
@Slf4j
public class HeartController {
    private final HeartService heartService;
    private final UserService userService;
    private final BoardService boardService;

    public HeartController(HeartService heartService, UserService userService, BoardService boardService) {
        this.heartService = heartService;
        this.userService = userService;
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseDTO<?> addHeart(HttpServletRequest request, @RequestBody Long BoardNo){
        User user = userService.getUser(request)
                .orElseThrow(()->new IllegalArgumentException("유저 정보가 없습니다."));
        Board board = boardService.findById(BoardNo);
        Heart heart = Heart.builder()
                .user(user)
                .board(board)
                .build();
        return heartService.insert(heart);
    }

    @DeleteMapping
    public ResponseDTO<?> delHeart(HttpServletRequest request, @RequestBody Long BoardNo){
        User user = userService.getUser(request)
                .orElseThrow(()->new IllegalArgumentException("유저 정보가 없습니다."));
        Board board = boardService.findById(BoardNo);
        Heart heart = Heart.builder()
                .user(user)
                .board(board)
                .build();
        return heartService.delete(heart);
    }
}
