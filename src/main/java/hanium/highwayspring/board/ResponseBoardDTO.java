package hanium.highwayspring.board;
import hanium.highwayspring.board.heart.Heart;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ResponseBoardDTO {
	private Board board;
	private Heart heart;

	public ResponseBoardDTO(Board board, Heart heart) {
		this.board = board;
		this.heart = heart;
	}
}
