package hanium.highwayspring.board.comment;

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
    private String userId;
    private Long parentId;
    private Boolean isDeleted;
    private List<CommentResponseDto> children = new ArrayList<>();
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public CommentResponseDto(Comment c) {
        this.id = c.getId();
        this.content = c.getContent();
        this.userId = c.getUser().getUid();
        this.createDate = c.getCreateDate();
        this.modifiedDate = c.getModifiedDate();
        this.isDeleted = c.getIsDeleted();
    }
}
