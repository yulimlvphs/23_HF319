package hanium.highwayspring.board;
import hanium.highwayspring.image.Image;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
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
	/*private Image image;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;*/

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
