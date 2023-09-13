package hanium.highwayspring.school.Curriculum;

import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class curriculumService {
    @Autowired
    private final curriculumRepository repository;

    public curriculumService(curriculumRepository repository) {
        this.repository = repository;
    }

    public List<DepartmentDTO> getGroupedCurriculum(Long schoolId) {
        List<Curriculum> curriculumList = repository.findAllBySchoolId(schoolId);

        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        for (Curriculum curriculum : curriculumList) {
            String department = curriculum.getDepart();

            // 해당 학과의 데이터를 찾거나 새로 생성
            DepartmentDTO departmentDTO = departmentDTOList.stream()
                    .filter(dto -> dto.getDepartment().equals(department))
                    .findFirst()
                    .orElseGet(() -> {
                        DepartmentDTO newDTO = new DepartmentDTO();
                        newDTO.setDepartment(department);
                        newDTO.setGrades(new ArrayList<>());
                        departmentDTOList.add(newDTO);
                        return newDTO;
                    });

            Map<String, Object> curriculumInfo = new HashMap<>();
            curriculumInfo.put("grade", curriculum.getGrade());
            curriculumInfo.put("content", curriculum.getContent());

            departmentDTO.getGrades().add(curriculumInfo);
        }

        return departmentDTOList;
    }

}
