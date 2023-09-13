package hanium.highwayspring.school.Curriculum;

import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class curriculumService {
    @Autowired
    private final curriculumRepository repository;

    public curriculumService(curriculumRepository repository) {
        this.repository = repository;
    }

    public Map<String, List<CurriculumInfo>> getGroupedCurriculum(Long schoolId) {
        List<Curriculum> curriculumList = repository.findAllBySchoolId(schoolId);

        // 학과별로 그룹화한 데이터 생성
        Map<String, List<CurriculumInfo>> curriculumMap = new HashMap<>();

        for (Curriculum curriculum : curriculumList) {
            String department = curriculum.getDepart();

            // 학과별로 List를 생성하고 그 안에 CurriculumInfo 객체를 추가
            curriculumMap.computeIfAbsent(department, k -> new ArrayList<>());

            CurriculumInfo curriculumInfo = new CurriculumInfo();
            curriculumInfo.setGrade(curriculum.getGrade());
            curriculumInfo.setContent(curriculum.getContent());

            curriculumMap.get(department).add(curriculumInfo);
        }

        return curriculumMap;
    }

}
