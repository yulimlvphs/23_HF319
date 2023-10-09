package hanium.highwayspring.board.DTO;
import lombok.Data;

// 게시글 상세 조회시에 프론트 팀의 요구사항에 맞춰 데이터를 반환하기 위해서 필요한 DTO
@Data
public class BoardHeartDTO {
    private Long boardId;
    private Long heartId;
    private String uId;

    public BoardHeartDTO(Long boardId, Long heartId, String uId) {
        this.boardId = boardId;
        this.heartId = heartId;
        this.uId = uId;
    }
}
