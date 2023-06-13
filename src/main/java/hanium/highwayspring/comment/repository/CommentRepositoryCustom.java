package hanium.highwayspring.comment.repository;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {
    List<Comment> findAllByBoard(Board board);
    Optional<Comment> findCommentByIdWithParent(Long id);
}