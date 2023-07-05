package hanium.highwayspring.board.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
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
        /*ResponseBoardDTO responseBoardDTO = jpaQueryFactory
                .select(Projections.constructor(ResponseBoardDTO.class, qBoard, qHeart))
                .from(qBoard)
                .leftJoin(qHeart)
                .on(qBoard.id.eq(qHeart.board.id))
                .on(qHeart.user.id.eq(userNo))
                .where(qBoard.id.eq(boardId))
                .fetchOne();*/
        ResponseBoardDTO responseBoardDTO = jpaQueryFactory
                .select(new QResponseBoardDTO(qBoard, qHeart))
                .from(qBoard)
                .leftJoin(qHeart)
                .on(qBoard.id.eq(qHeart.board.id))
                .on(qHeart.user.id.eq(userNo))
                .where(qBoard.id.eq(boardId))
                .fetchOne();
        return Optional.ofNullable(responseBoardDTO);
    }
}
