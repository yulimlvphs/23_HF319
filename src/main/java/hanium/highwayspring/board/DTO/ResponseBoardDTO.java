package hanium.highwayspring.board.DTO;
import com.querydsl.core.annotations.QueryProjection;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.heart.Heart;
import hanium.highwayspring.board.heart.QHeart;
import hanium.highwayspring.image.Image;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class ResponseBoardDTO { /*  findBoardDetail 메소드에서 사용하는 DTO  */
	private Board board;
	private Heart heart;
	private Long userNo;
	private String userName;
	List<String> imageUrls;

	@QueryProjection
	public ResponseBoardDTO(Board board, Heart heart, User user) {
		this.board = board;
		this.heart = heart;
		this.userNo = user.getId();
		this.userName = user.getName();
	}
}
