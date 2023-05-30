package hanium.highwayspring.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userId;
    private Long parentId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
