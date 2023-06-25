package hanium.highwayspring.school.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeartDTO {
    private Long id;
    private Long schoolId;
    private String uid;
}
