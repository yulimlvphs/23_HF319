package hanium.highwayspring.school;

import hanium.highwayspring.dept.Dept;
import hanium.highwayspring.dept.DeptDTO;
import hanium.highwayspring.tag.Tag;
import hanium.highwayspring.tag.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseSchoolDTO {
    Optional<School> sch;
    private List<TagDTO> tag;
    private List<DeptDTO> dept;
}
