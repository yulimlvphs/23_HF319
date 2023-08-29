package hanium.highwayspring.image;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class imageRepositoryImpl implements imageRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public imageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public void deleteImageForUpdate(Long boardId, String urlValue){
        QImage qimage = QImage.image;

        jpaQueryFactory
                .delete(qimage)
                .where(
                qimage.boardId.eq(boardId), qimage.imageUrl.eq(urlValue))
                .execute();
    }
}
