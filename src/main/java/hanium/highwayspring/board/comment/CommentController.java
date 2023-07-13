package hanium.highwayspring.board.comment;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequestMapping("/comment")
@RestController
@Slf4j
public class CommentController {

    private final UserService userService;
    private final CommentService commentService;

    public CommentController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseDTO<?> createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        Optional<User> user = userService.getUser(request);
        requestDto.setUserId(user.get());
        return commentService.createComment(requestDto);
    }

    @GetMapping("/list/{id}")
    public ResponseDTO<?> retrieveCommentList(@PathVariable(name = "id") Long boardId) {
        return commentService.getAllCommentsByBoard(boardId);
    }

    @PutMapping
    public ResponseDTO<?> updateComment(CommentRequestDto requestDto){
        return commentService.updateComment(requestDto);
    }

    @PutMapping("/delete")
    public ResponseDTO<?> sDeleteComment(CommentRequestDto requestDto){
        return commentService.deleteComment(requestDto.getId());
    }

    @DeleteMapping
    public ResponseDTO<?> deleteComment(CommentRequestDto requestDto){
        return commentService.deleteComment(requestDto.getId());
    }
}
