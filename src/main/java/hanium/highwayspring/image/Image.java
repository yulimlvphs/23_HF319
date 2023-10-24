package hanium.highwayspring.image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity(name = "image_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Image 객체 pk

    @Column
    private Long boardId;

    @Column
    private String imageUrl;

    public Image(Long boardId, String url) {
        this.boardId = boardId;
        this.imageUrl = url;
    }
}

