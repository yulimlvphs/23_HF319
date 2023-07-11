package hanium.highwayspring.school;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.ResponseBoardDTO;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.dept.Dept;
import hanium.highwayspring.dept.DeptDTO;
import hanium.highwayspring.dept.DeptRepository;
import hanium.highwayspring.tag.QTag;
import hanium.highwayspring.tag.Tag;
import hanium.highwayspring.tag.TagDTO;
import hanium.highwayspring.tag.TagRepository;
import hanium.highwayspring.user.QUser;
import hanium.highwayspring.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Transactional
@Slf4j
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final TagRepository tagRepository;
    private final DeptRepository deptRepository;
    @Autowired
    private EntityManager entityManager;

    public Optional<School> findBySchoolId(Long id) {
        Optional<School> sch = schoolRepository.findById(id);
        return sch;
    }

    public Optional<ResponseSchoolDTO> getSchoolInfo(Long id) {
        Optional<School> sch = schoolRepository.findById(id); // 학교 데이터 찾기

        ResponseSchoolDTO responseSchoolDTO = new ResponseSchoolDTO(); //DTO를 생성해
        List<TagDTO> tags = tagRepository.findNameAndCodeById(id); //tag 테이블에서 name, code 정보만 걸러냄
        List<DeptDTO> depts = deptRepository.findNameById(id);
        responseSchoolDTO.setSch(sch); //DTO에 sch 값을 넣고
        responseSchoolDTO.setTag(tags); //DTO에 있는 tag 필드에 조회한 tags 값을 넣음.
        responseSchoolDTO.setDept(depts); //DTO에 있는 dept 필드에 조회한 depts 값을 넣음.

        return Optional.of(responseSchoolDTO);
    }

    //school 테이블에서 원하는 속성과 tag 테이블에서 원하는 속성을 골라 하나의 객체로 반환하는 메소드입니다.
    public List<SchoolInfoDTO> findSchoolInfoWithTagsAndUserCount() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QSchool school = QSchool.school;
        QTag tag = QTag.tag;
        QUser user = QUser.user;

        List<SchoolInfoDTO> schoolInfoList = queryFactory
                .select(Projections.constructor(SchoolInfoDTO.class,
                        school.id, school.schoolName))
                .from(school)
                .leftJoin(tag).on(school.id.eq(tag.schoolId))
                .leftJoin(user).on(school.id.eq(user.schoolId.id))
                .groupBy(school.id, school.schoolName)
                .fetch();

        for (SchoolInfoDTO schoolInfo : schoolInfoList) {
            List<String> tags = queryFactory
                    .select(tag.name)
                    .from(tag)
                    .where(tag.schoolId.eq(schoolInfo.getId()))
                    .fetch();

            schoolInfo.setTag(tags);

            Long userCount = queryFactory
                    .select(user.id.count())
                    .from(user)
                    .where(user.schoolId.id.eq(schoolInfo.getId()))
                    .fetchOne();

            schoolInfo.setUserCount(userCount != null ? userCount.intValue() : 0);
        }

        return schoolInfoList;
    }
}