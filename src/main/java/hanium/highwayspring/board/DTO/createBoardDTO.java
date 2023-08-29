package hanium.highwayspring.board.DTO;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.heart.Heart;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Data
public class createBoardDTO {
    private Board board;
    List<String> imageUrls;

    public createBoardDTO(Board board) {
        this.board = board;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
