package hanium.highwayspring.comment;

import hanium.highwayspring.config.res.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Test
    void 댓글작성() {
        String uid = "admin";
        String content = "내용";
        Long boardId = 41L;
        Long parentId = 1L;
        CommentRequestDto dto = CommentRequestDto.builder()
                .userId(uid)
                .content(content)
                .boardId(boardId)
                .parentId(parentId)
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