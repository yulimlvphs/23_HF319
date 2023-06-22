package hanium.highwayspring.board.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.comment.Comment;
import hanium.highwayspring.comment.QComment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Comment> findAllByBoard(Board board) {
        QComment comment = QComment.comment;
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.board.id.eq(board.getId()))
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.createDate.asc())
                .fetch();
    }
    @Override
    public Optional<Comment> findCommentByIdWithParent(Long id) {
        QComment comment = QComment.comment;
        Comment selectedComment = jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(selectedComment);
    }
}
