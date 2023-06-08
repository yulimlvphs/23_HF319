package hanium.highwayspring.comment;

import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private User userId;
    private Long parentId;
    private List<CommentResponseDto> children = new ArrayList<>();
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public CommentResponseDto(Comment c) {
        this.id = c.getId();
        this.content = c.getContent();
        this.userId = c.getUserId();
        this.createDate = c.getCreateDate();
        this.modifiedDate = c.getModifiedDate();
    }
}
