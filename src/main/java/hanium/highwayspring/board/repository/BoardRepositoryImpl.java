package hanium.highwayspring.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.QBoard;
import hanium.highwayspring.board.QResponseBoardDTO;
import hanium.highwayspring.board.ResponseBoardDTO;
import hanium.highwayspring.board.heart.QHeart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Board> findBoardList(Long schId, Long cateNo) {
        QBoard qBoard = QBoard.board;
        List<Board> list = jpaQueryFactory
                .selectFrom(qBoard)
                .where(qBoard.school.id.eq(schId))
                .where(qBoard.category.eq(cateNo))
                .fetch();
        return list;
    }

    @Override
    public Optional<ResponseBoardDTO> findBoardDetail(Long userNo, Long boardId) {
        QBoard qBoard = QBoard.board;
        QHeart qHeart = QHeart.heart;
        ResponseBoardDTO responseBoardDTO = jpaQueryFactory
                .select(new QResponseBoardDTO(qBoard, qHeart, qBoard.user))
                .from(qBoard)
                .leftJoin(qHeart)
                .on(qBoard.id.eq(qHeart.board.id))
                .on(qHeart.user.id.eq(userNo))
                .fetchJoin()
                .where(qBoard.id.eq(boardId))
                .fetchOne();
        return Optional.ofNullable(responseBoardDTO);
    }

    @Override
    public List<Board> findBoardHeartList(Long uId) {
        QBoard qBoard = QBoard.board;
        QHeart qHeart = QHeart.heart;
        List<Board> list = jpaQueryFactory
                .select(qBoard)
                .from(qBoard)
                .innerJoin(qHeart)
                .on(qHeart.board.eq(qBoard))
                .where(qHeart.user.id.eq(uId))
                .fetch();
        return list;
    }
}
