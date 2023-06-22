package hanium.highwayspring.comment;

import hanium.highwayspring.board.comment.CommentRequestDto;
import hanium.highwayspring.board.comment.CommentService;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @Test
    void 댓글작성() {
        String uid = "admin";
        String content = "내용";
        Long boardId = 41L;
        User user = userService.findByUid(uid);
//        Long parentId = 1L;
        CommentRequestDto dto = CommentRequestDto.builder()
                .userId(user)
                .content(content)
                .boardId(boardId)
//                .parentId(parentId)
                .build();
        ResponseDTO<?> responseDTO = commentService.createComment(dto);
        System.out.println(responseDTO.getData());
    }

    @Test
    void 댓글삭제() {
        Long commentId = 2L;
        ResponseDTO<?> responseDTO = commentService.deleteComment(commentId);
        System.out.println(responseDTO.getData());
    }
}