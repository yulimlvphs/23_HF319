package hanium.highwayspring.dept;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class DeptDTO {
    private String deptName;
    private String description;

    public DeptDTO(String deptName, String description) {
        this.deptName = deptName;
        this.description = description;
    }
}
