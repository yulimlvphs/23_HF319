package hanium.highwayspring.comment;

import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.UserDTO;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseDTO<?> createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        UserDTO user = userService.getUserInfo(request);
        requestDto.setUserId(user.getUserId());
        return commentService.createComment(requestDto);
    }

    @GetMapping
    public ResponseDTO<?> retrieveCommentList(@RequestParam(name = "boardId") Long boardId) {
        return commentService.getAllCommentsByBoard(boardId);
    }

    @PutMapping
    public ResponseDTO<?> updateComment(CommentRequestDto requestDto){
        return commentService.updateComment(requestDto);
    }

    @DeleteMapping
    public ResponseDTO<?> deleteComment(CommentRequestDto requestDto){
        return commentService.deleteComment(requestDto.getId());
    }
}
