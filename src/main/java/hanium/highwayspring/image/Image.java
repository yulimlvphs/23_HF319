package hanium.highwayspring.image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "image_TB")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Image 객체 pk

    @Column
    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
