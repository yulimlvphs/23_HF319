package hanium.highwayspring.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class TagDTO {
    private String tagName;
    private Long tagCode;

    public TagDTO(String tagName, Long tagCode) {
        this.tagName = tagName;
        this.tagCode = tagCode;
    }
}
