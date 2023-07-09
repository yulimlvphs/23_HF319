package hanium.highwayspring.tag;

import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@NoArgsConstructor
@Data
@Setter
@Getter
public class TagDTO {
    private String tagName;
    private Long tagCode;

    public TagDTO(String tagName, Long tagCode) {
        this.tagName = tagName;
        this.tagCode = tagCode;
    }
}
