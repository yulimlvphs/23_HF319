package hanium.highwayspring.school.Curriculum;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private String department;
    private List<CurriculumInfo> grades;
}
