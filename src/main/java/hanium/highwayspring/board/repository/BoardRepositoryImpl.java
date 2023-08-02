package hanium.highwayspring.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.*;
import hanium.highwayspring.board.heart.QHeart;
import hanium.highwayspring.image.QImage;
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
        QImage qImage = QImage.image;

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
        QImage qImage = QImage.image;
        ResponseBoardDTO responseBoardDTO = jpaQueryFactory
                .select(new QResponseBoardDTO(qBoard, qHeart, qBoard.user))
                .from(qBoard)
                .leftJoin(qHeart)
                .on(qBoard.id.eq(qHeart.board.id))
                .on(qHeart.user.id.eq(userNo))
                .fetchJoin()
                .where(qBoard.id.eq(boardId))
                .fetchOne();

        List<String> imageUrls = jpaQueryFactory
                .select(qImage.imageUrl)
                .from(qImage)
                .where(qImage.boardId.eq(boardId))
                .fetch();

        assert responseBoardDTO != null; //responseBoardDTO의 null값 여부 확인
        responseBoardDTO.setImageUrls(imageUrls);

        return Optional.of(responseBoardDTO);
    }

    @Override
    public List<Board> findBoardHeartList(Long uId) {
        QBoard qBoard = QBoard.board;
        QHeart qHeart = QHeart.heart;
        List<Board> list = jpaQueryFactory
                .selectFrom(qBoard)
                .innerJoin(qHeart)
                .on(qHeart.board.eq(qBoard))
                .where(qBoard.user.id.eq(uId))
                .fetch();
        return list;
    }

    /*@Override
    public Board findByIdWithImage(Long boardId) {
        QBoard qBoard = QBoard.board;
        QImage qImage = QImage.image;

        Board board = jpaQueryFactory
                .selectFrom(qBoard)
                .leftJoin(qBoard.images, qImage)
                .fetchJoin()
                .where(qImage.boardId.eq(boardId))
                .fetchOne();

        return board;
    }*/
}
