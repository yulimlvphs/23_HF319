package hanium.highwayspring.dept;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class DeptDTO {
    private String deptName;

    public DeptDTO(String dept) {
        this.deptName = deptName;
    }
}
