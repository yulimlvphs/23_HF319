package hanium.highwayspring.comment.repository;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom{
    @Override
    List<Comment> findAllByBoard(Board board);
}
