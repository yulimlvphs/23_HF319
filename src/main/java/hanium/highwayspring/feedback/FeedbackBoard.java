package hanium.highwayspring.feedback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private Integer category;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JoinColumn(name = "user_name")
    private String userName;

    @Column(name = "response")
    private String response;
}
