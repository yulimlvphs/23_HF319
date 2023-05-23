package hanium.highwayspring.board;

import hanium.highwayspring.school.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private Long category;

	public BoardDTO(final Board entity) {
		this.id 	= entity.getId();
		this.title 	= entity.getTitle();
		this.content = entity.getContent();
		this.category = entity.getCategory();
	}
	
	public static Board toEntity(final BoardDTO dto, final Optional<School> school) {
		School Entity = School.builder()
				.id(school.get().getId())
				.build();
		return Board.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.content(dto.getContent())
				.category(dto.getCategory())
				.school(Entity)
				.build();
	}
}
