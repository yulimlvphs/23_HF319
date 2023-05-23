package hanium.highwayspring.board;

import javax.persistence.*;

import hanium.highwayspring.school.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
    private String userId;
    private String title;
    private String content;
    private Long category;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "school_id", referencedColumnName = "id"),
            @JoinColumn(name = "school_name", referencedColumnName = "SCHUL_NM")
    })
    private School school;
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createDate;
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime modifiedDate;
}
