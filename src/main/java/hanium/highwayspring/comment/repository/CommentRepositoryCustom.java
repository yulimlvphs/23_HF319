package hanium.highwayspring.comment.repository;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.comment.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findAllByBoard(Board board);
}