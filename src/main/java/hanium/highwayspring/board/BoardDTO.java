package hanium.highwayspring.board;

import hanium.highwayspring.school.School;
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

	public static Board toEntity(final BoardDTO dto, final School school) {
		return Board.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.content(dto.getContent())
				.category(dto.getCategory())
				.school(school)
				.build();
	}
}
