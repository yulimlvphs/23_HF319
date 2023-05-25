package hanium.highwayspring.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequestDto {
	private Long id;
	private String content;
	private Long boardId;
	private Long parentId;
}
