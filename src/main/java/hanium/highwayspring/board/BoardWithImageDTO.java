package hanium.highwayspring.board;

import java.util.List;

public class BoardWithImageDTO {
    private Board board;
    private List<String> imageUrls;

    public BoardWithImageDTO(Board board, List<String> imageUrls) {
        this.board = board;
        this.imageUrls = imageUrls;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

}
