package hanium.highwayspring.school;

import com.querydsl.core.Tuple;
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

    public Optional<School> findBySchoolId(Long id){
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