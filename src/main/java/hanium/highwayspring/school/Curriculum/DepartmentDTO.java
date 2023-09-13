package hanium.highwayspring.school.Curriculum;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DepartmentDTO {
    private String department;
    private List<Map<String, Object>> grades;
}
