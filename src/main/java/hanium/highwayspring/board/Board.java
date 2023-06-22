package hanium.highwayspring.board;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.board.comment.Comment;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "board_TB")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //이 오브젝트의 아이디
    private String title;
    private String content;
    private Long category;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "schoolId")
    @JsonIgnore
    private School school;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createDate;
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime modifiedDate;

    public void updateBoard(BoardDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
    }
}
