package hanium.highwayspring.board.repository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.*;
import hanium.highwayspring.board.DTO.BoardWithImageDTO;
import hanium.highwayspring.board.DTO.QResponseBoardDTO;
import hanium.highwayspring.board.DTO.ResponseBoardDTO;
import hanium.highwayspring.board.DTO.createBoardDTO;
import hanium.highwayspring.board.heart.QHeart;
import hanium.highwayspring.image.QImage;
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

        // 게시글 조회
        List<Board> boards = jpaQueryFactory
                .selectFrom(qBoard)
                .where(qBoard.school.id.eq(schId))
                .where(qBoard.category.eq(cateNo))
                .fetch();

        // 게시글의 이미지 조회
        List<BoardWithImageDTO> resultList = boards.stream()
                .map(board -> {
                    List<String> imageUrls = jpaQueryFactory
                            .select(qImage.imageUrl)
                            .from(qImage)
                            .where(qImage.boardId.eq(board.getId())) // 이미지의 boardId와 매칭되어야 합니다.
                            .fetch();
                    return new BoardWithImageDTO(board, imageUrls);
                })
                .collect(Collectors.toList());

        return resultList;
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

        if (responseBoardDTO == null) {
            return Optional.empty(); // 결과가 없으면 빈 Optional 반환
        }

        List<String> imageUrls = jpaQueryFactory
                .select(qImage.imageUrl)
                .from(qImage)
                .where(qImage.boardId.eq(boardId))
                .fetch();

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
