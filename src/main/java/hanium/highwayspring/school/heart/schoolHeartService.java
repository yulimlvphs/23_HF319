package hanium.highwayspring.school.heart;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.QSchool;
import hanium.highwayspring.tag.QTag;
import hanium.highwayspring.user.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import hanium.highwayspring.school.heart.QHeart;
@Service
@RequiredArgsConstructor
public class schoolHeartService {
    private final schoolHeartRepository heartRepository;
    @Autowired
    private EntityManager entityManager;

    public ResponseDTO<?> insert(final Heart heart){
        heartRepository.save(heart);
        SchoolHeartInsertDTO dto = SchoolHeartInsertDTO.builder()
                .heartId(heart.getId())
                .schoolId(heart.getSchool().getId())
                .build();
        return ResponseDTO.success(dto);
    }

    public  ResponseDTO<?> delete(Long heartId){
        heartRepository.deleteById(heartId);
        return ResponseDTO.success("success");
    }

    public ResponseDTO<?> findAll(Long userId) {
        List<Heart> hearts = heartRepository.findAllByUserId(userId);
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QSchool school = QSchool.school;
        QTag tag = QTag.tag;
        QUser user = QUser.user;
        QHeart qHeart = QHeart.heart;

        List<SchoolHeartDTO> schoolInfoList = new ArrayList<>();

        for (Heart heart1 : hearts) {
            Long schoolId = heart1.getSchool().getId();

            List<SchoolHeartDTO> schoolInfo = queryFactory
                    .select(Projections.constructor(SchoolHeartDTO.class, school.id, school.schoolName))
                    .from(school)
                    .leftJoin(tag).on(school.id.eq(tag.schoolId))
                    .leftJoin(user).on(school.id.eq(user.schoolId.id))
                    .where(school.id.eq(schoolId)) // 해당 schoolId에 해당하는 학교만 조회
                    .groupBy(school.id, school.schoolName)
                    .fetch();

            schoolInfoList.addAll(schoolInfo);
        }

        for (SchoolHeartDTO schoolInfo : schoolInfoList) {
            List<String> tags = queryFactory
                    .select(tag.name)
                    .from(tag)
                    .where(tag.schoolId.eq(schoolInfo.getSchoolId()))
                    .fetch();

            schoolInfo.setTag(tags);

            Long heart = queryFactory
                    .select(qHeart.id)
                    .from(qHeart)
                    .where(qHeart.school.id.eq(schoolInfo.getSchoolId()))
                    .where(qHeart.user.id.eq(userId))
                    .fetchOne();

            schoolInfo.setHeartId(heart);

            Long userCount = queryFactory
                    .select(user.id.count())
                    .from(user)
                    .where(user.schoolId.id.eq(schoolInfo.getSchoolId()))
                    .fetchOne();

            schoolInfo.setUserCount(userCount != null ? userCount.intValue() : 0);
        }

        return ResponseDTO.success(schoolInfoList);
    }

    public boolean existsByUserIdAndSchoolId(Long userId, Long schoolId) {
        return heartRepository.existsByUserIdAndSchoolId(userId, schoolId);
    }

    public long countByUserId(Long userId) {
        return heartRepository.countByUserId(userId);
    }
}
