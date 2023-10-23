package hanium.highwayspring.feedback;

import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.DTO.BoardDTO;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class feedbackDTO {
    private Long id;
    private String title;
    private String content;
    private Integer category;
    private Long userId;
    private String userName;
    private String response;

    public static FeedbackBoard toEntity(final feedbackDTO dto, final User user) {
        return FeedbackBoard.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .user(user)
                .userName(user.getName())
                .response(dto.getResponse())
                .build();
    }
}
