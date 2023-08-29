package hanium.highwayspring.board.DTO;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private Long category;
	private List<String> imageList;

	public List<String> getImageList() {
		return imageList;
	}

	public static Board toEntity(final BoardDTO dto, final User user) {
		return Board.builder()
				.title(dto.getTitle())
				.content(dto.getContent())
				.category(dto.getCategory())
				.user(user)
				.school(user.getSchoolId())
				.build();
	}
}
