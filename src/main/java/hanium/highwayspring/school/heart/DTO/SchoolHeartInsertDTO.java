package hanium.highwayspring.school.heart.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SchoolHeartInsertDTO {
    private Long heartId;
    private Long schoolId;
}
