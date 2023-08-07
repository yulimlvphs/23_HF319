package hanium.highwayspring.board.DTO;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private Long category;

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
