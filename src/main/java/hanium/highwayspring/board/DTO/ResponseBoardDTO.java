package hanium.highwayspring.board.DTO;
import com.querydsl.core.annotations.QueryProjection;
import hanium.highwayspring.board.Board;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Data
public class ResponseBoardDTO {
	private Board board;
	private Long userNo;
	private String userName;
	private Long heartCount;
	private List<BoardHeartDTO> boardHeartInfo;
	private List<String> imageUrls;

	@QueryProjection
	public ResponseBoardDTO(Board board, Long userNo, String userName, Long heartCount) {
		this.board = board;
		this.userNo = userNo;
		this.userName = userName;
		this.heartCount = heartCount;
	}
}