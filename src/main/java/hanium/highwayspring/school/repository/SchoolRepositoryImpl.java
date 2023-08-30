package hanium.highwayspring.school.repository;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.QSchool;
import hanium.highwayspring.school.DTO.SchoolInfoDTO;
import hanium.highwayspring.tag.QTag;
import hanium.highwayspring.user.QUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolRepositoryImpl implements SchoolRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public SchoolRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //school 리스트를 반환하는 메소드
    public ResponseDTO<?> findSchoolInfoWithTagsAndUserCount() {

        QSchool school = QSchool.school;
        QTag tag = QTag.tag;
        QUser user = QUser.user;

        List<SchoolInfoDTO> schoolInfoList = jpaQueryFactory
                .selectFrom(school)
                .leftJoin(tag).on(tag.schoolId.eq(school.id))
                .leftJoin(user).on(user.schoolId.id.eq(school.id))
                .groupBy(school.id, school.schoolName,tag.name)
                .transform(GroupBy.groupBy(school.id).list(Projections.constructor(
                        SchoolInfoDTO.class,
                        school.id,
                        school.schoolName,
                        user.schoolId.id.count().intValue(),
                        school.schoolImage,
                        GroupBy.list(tag.name)
                )));

        return ResponseDTO.success(schoolInfoList);
    }
}
