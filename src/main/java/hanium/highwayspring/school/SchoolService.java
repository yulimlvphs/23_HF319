package hanium.highwayspring.school;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.dept.DeptDTO;
import hanium.highwayspring.dept.DeptRepository;
import hanium.highwayspring.school.DTO.ResponseSchoolDTO;
import hanium.highwayspring.school.repository.SchoolRepository;
import hanium.highwayspring.tag.TagDTO;
import hanium.highwayspring.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;
import static com.querydsl.core.group.GroupBy.list;


@Transactional
@Slf4j
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final TagRepository tagRepository;
    private final DeptRepository deptRepository;

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

    public ResponseDTO<?> findAll() {
        return schoolRepository.findSchoolInfoWithTagsAndUserCount();
    }
}