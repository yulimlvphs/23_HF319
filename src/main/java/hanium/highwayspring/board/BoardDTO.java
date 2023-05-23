package hanium.highwayspring.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	private String id; 			
	private String title;
	private String content;

	public BoardDTO(final Board entity) {
		this.id 	= entity.getId();
		this.title 	= entity.getTitle();
		this.content = entity.getContent();
	}
	
	public static Board toEntity(final BoardDTO dto) {
		return Board.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.content(dto.getContent())
				.build();
	}
}
