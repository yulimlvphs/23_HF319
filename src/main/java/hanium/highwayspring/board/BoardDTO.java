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

	public BoardDTO(final BoardEntity entity) {
		this.id 	= entity.getId();
		this.title 	= entity.getTitle();
	}
	
	public static BoardEntity toEntity(final BoardDTO dto) {
		return BoardEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.build();
	}
}
