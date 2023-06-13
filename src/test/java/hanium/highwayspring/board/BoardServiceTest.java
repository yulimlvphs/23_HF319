package hanium.highwayspring.board;

import hanium.highwayspring.comment.CommentRequestDto;
import hanium.highwayspring.comment.CommentService;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
//@Transactional
class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;
    @Autowired
    SchoolService schoolService;

    @Test
    void 글작성() {
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        String uid = "admin";
        String title = "제목";
        String content = "내용";
        Long category = 1L;
        Board board = Board.builder()
                .userId(uid)
                .school(school)
                .title(title)
                .content(content)
                .category(category)
                .build();
        boardService.create(board);
    }

    @Test
    void 글삭제() {
        String uid = "admin";
        /*List<Board> boards = boardService.retrieve(uid);
        boardService.delete(boards.get(0));*/
        boardService.delete(40L);
    }
}