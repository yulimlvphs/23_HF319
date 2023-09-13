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
            Long grade = curriculum.getGrade();
            String content = curriculum.getContent();

            DepartmentDTO departmentDTO = null;

            for (DepartmentDTO dto : departmentDTOList) {
                if (dto.getDepartment().equals(department)) {
                    departmentDTO = dto;
                    break;
                }
            }

            if (departmentDTO == null) {
                departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartment(department);
                departmentDTO.setGrades(new ArrayList<>());
                departmentDTOList.add(departmentDTO);
            }

            CurriculumInfo curriculumInfo = new CurriculumInfo();
            curriculumInfo.setGrade(grade);
            curriculumInfo.setContent(content);

            departmentDTO.getGrades().add(curriculumInfo);
        }

        return departmentDTOList;
    }

}
