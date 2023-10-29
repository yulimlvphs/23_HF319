package hanium.highwayspring.board.repository;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.*;
import hanium.highwayspring.board.DTO.*;
import hanium.highwayspring.board.heart.QHeart;
import hanium.highwayspring.image.QImage;
import hanium.highwayspring.user.QUser;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<BoardWithImageDTO> findAllBoardList(Long schId, Long cateNo) {
        QBoard qBoard = QBoard.board;
        QImage qImage = QImage.image;

        JPAQuery<Board> query = jpaQueryFactory.selectFrom(qBoard);

        // schId 값이 null인 경우, schoolId가 null인 레코드를 포함하여 조회
        if (schId == null) {
            query.where(qBoard.school.id.isNull());
        } else {
            query.where(qBoard.school.id.eq(schId));
        }

        // cateNo 조건은 항상 추가
        query.where(qBoard.category.eq(cateNo));

        List<Board> boards = query.fetch();

        List<BoardWithImageDTO> resultList = boards.stream()
                .map(board -> {
                    List<String> imageUrls = jpaQueryFactory
                            .select(qImage.imageUrl)
                            .from(qImage)
                            .where(qImage.boardId.eq(board.getId()))
                            .fetch();
                    return new BoardWithImageDTO(board, imageUrls);
                })
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public Optional<ResponseBoardDTO> findBoardDetail(Long userNo, Long boardId) {
        QBoard qBoard = QBoard.board;
        QUser qUser = QUser.user;
        QImage qImage = QImage.image;
        QHeart qHeart = QHeart.heart;

        // 게시물 정보 조회
        ResponseBoardDTO responseBoardDTO = jpaQueryFactory
                .select(Projections.constructor(
                        ResponseBoardDTO.class,
                        qBoard,
                        qUser.id,
                        qUser.name,
                        JPAExpressions //이 부분은 게시글의 좋아요 개수를 count
                                .select(qHeart.count())
                                .from(qHeart)
                                .where(qHeart.board.id.eq(boardId))
                ))
                .from(qBoard)
                .innerJoin(qBoard.user, qUser)
                .on(qBoard.id.eq(boardId))
                .fetchOne();

        if (responseBoardDTO == null) {
            return Optional.empty();
        }

        // 게시물 좋아요 정보 + 추가적인 데이터 요구 사항 함께 반환
        List<BoardHeartDTO> boardHeartInfo = jpaQueryFactory
                .select(Projections.constructor(
                        BoardHeartDTO.class,
                        qBoard.id,
                        qHeart.id,
                        qHeart.user.uid
                ))
                .from(qHeart)
                .where(qHeart.board.id.eq(boardId))
                .fetch();

        // 이미지 URL 조회
        List<String> imageUrls = jpaQueryFactory
                .select(qImage.imageUrl)
                .from(qImage)
                .where(qImage.boardId.eq(boardId))
                .fetch();

        responseBoardDTO.setBoardHeartInfo(boardHeartInfo);
        responseBoardDTO.setImageUrls(imageUrls);

        return Optional.of(responseBoardDTO);
    }



    @Override
    public Optional<createBoardDTO> findBoardCreate(Long boardId) { //게시판 생성시 반환해주는 메서드
        QBoard qBoard = QBoard.board;
        QImage qImage = QImage.image;
        createBoardDTO dto = jpaQueryFactory
                .select(Projections.constructor(createBoardDTO.class, qBoard))
                .from(qBoard)
                .where(qBoard.id.eq(boardId))
                .fetchOne();

        List<String> imageUrls = jpaQueryFactory
                .select(qImage.imageUrl)
                .from(qImage)
                .where(qImage.boardId.eq(boardId))
                .fetch();

        assert dto != null; //responseBoardDTO의 null값 여부 확인
        dto.setImageUrls(imageUrls);

        return Optional.of(dto);
    }

    @Override
    public List<Board> findBoardHeartList(Long uId) {
        QBoard qBoard = QBoard.board;
        QHeart qHeart = QHeart.heart;
        List<Board> list = jpaQueryFactory
                .selectFrom(qBoard)
                .innerJoin(qHeart)
                .on(qHeart.board.eq(qBoard))
                .where(qHeart.user.id.eq(uId))
                .fetch();
        return list;
    }

    @Override
    public List<BoardWithImageDTO> findAllBoardByUserId(Long userId) { //유저가 작성한 글 List 보기
        QBoard qBoard = QBoard.board;
        QImage qImage = QImage.image;

        // 게시글 조회
        List<Board> boards = jpaQueryFactory
                .selectFrom(qBoard)
                .where(qBoard.user.id.eq(userId)) // 해당 userId와 일치하는 게시글만 선택
                .fetch();

        // 게시글의 이미지 조회
        List<BoardWithImageDTO> resultList = boards.stream()
                .map(board -> {
                    List<String> imageUrls = jpaQueryFactory
                            .select(qImage.imageUrl)
                            .from(qImage)
                            .where(qImage.boardId.eq(board.getId()))
                            .fetch();
                    return new BoardWithImageDTO(board, imageUrls);
                })
                .collect(Collectors.toList());

        return resultList;
    }
}
