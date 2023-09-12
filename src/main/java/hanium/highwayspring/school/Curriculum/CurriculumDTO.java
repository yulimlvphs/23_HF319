package hanium.highwayspring.school.Curriculum;
import lombok.Data;
import java.util.List;

@Data
public class CurriculumDTO {
    private String depart;
    private List<CurriculumGradeDTO> grades;
}
