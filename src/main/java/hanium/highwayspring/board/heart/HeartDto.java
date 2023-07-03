package hanium.highwayspring.board.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeartDto {
    private Long id;
    private Long boardId;
    private String uid;
}
