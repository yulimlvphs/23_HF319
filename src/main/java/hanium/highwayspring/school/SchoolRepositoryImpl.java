package hanium.highwayspring.school;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.tag.QTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SchoolRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SchoolRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Autowired
    private EntityManager entityManager;

    //school 테이블에서 원하는 속성과 tag 테이블에서 원하는 속성을 골라 하나의 객체로 반환하는 메소드입니다.
    public List<SchoolInfoDTO> findSchoolInfoWithTags() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QSchool school = QSchool.school;
        QTag tag = QTag.tag;

        JPQLQuery<Tuple> query = queryFactory
                .select(school.id, school.schoolName, school.websiteAddress, tag.name)
                .from(school)
                .leftJoin(tag).on(school.id.eq(tag.schoolId));

        List<Tuple> rows = query.fetch();

        Map<Long, SchoolInfoDTO> schoolMap = new HashMap<>();
        for (Tuple row : rows) {
            Long schoolId = row.get(school.id);
            String schoolName = row.get(school.schoolName);
            String websiteAddress = row.get(school.websiteAddress);
            String tagName = row.get(tag.name);

            SchoolInfoDTO schoolDTO = schoolMap.get(schoolId);
            if (schoolDTO == null) {
                schoolDTO = new SchoolInfoDTO(schoolId, schoolName, websiteAddress);
                schoolMap.put(schoolId, schoolDTO);
            }

            if (tagName != null) {
                schoolDTO.getTag().add(tagName);
            }
        }

        return new ArrayList<>(schoolMap.values());
    }
}