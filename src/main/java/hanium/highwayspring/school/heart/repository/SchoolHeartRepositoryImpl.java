package hanium.highwayspring.school.heart.repository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.QSchool;
import hanium.highwayspring.school.heart.dto.SchoolHeartDTO;
import hanium.highwayspring.tag.QTag;
import hanium.highwayspring.user.QUser;
import org.springframework.stereotype.Service;
import com.querydsl.core.group.GroupBy;

import java.util.List;
@Service
public class SchoolHeartRepositoryImpl implements SchoolHeartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public SchoolHeartRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public ResponseDTO<?> findAll(Long userId) {
        QSchool school = QSchool.school;
        QTag tag = QTag.tag;
        QUser user = QUser.user;
        hanium.highwayspring.school.heart.QHeart qHeart = hanium.highwayspring.school.heart.QHeart.heart;

        List<SchoolHeartDTO> schoolHeartDTOList = jpaQueryFactory
                .selectFrom(qHeart)
                .where(qHeart.user.id.eq(userId))
                .leftJoin(tag).on(tag.schoolId.eq(qHeart.school.id))
                .leftJoin(user).on(user.schoolId.id.eq(qHeart.school.id))
                .leftJoin(school).on(school.id.eq(qHeart.school.id))
                .groupBy(qHeart.id, qHeart.school.id, school.schoolName, tag.name)
                .transform(GroupBy.groupBy(qHeart.id).list(Projections.constructor(
                        SchoolHeartDTO.class,
                        qHeart.id,
                        qHeart.school.id,
                        school.schoolName,
                        user.schoolId.id.count().intValue(),
                        GroupBy.list(tag.name)
                )));

        return ResponseDTO.success(schoolHeartDTOList);
    }
}
