package hanium.highwayspring.board;

import hanium.highwayspring.board.comment.CommentService;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    UserService userService;

    @Test
    void 글작성() {
        School school = schoolService.findBySchoolId(1L)
                .orElseThrow(() -> new IllegalArgumentException("학교가 존재하지 않습니다."));
        String uid = "admin";
        String title = "제목";
        String content = "내용";
        Long category = 1L;
        User user = userService.findByUid(uid);
        Board board = Board.builder()
                .user(user)
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