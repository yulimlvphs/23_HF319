package hanium.highwayspring.board.heart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity(name = "boardheart_tb")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "boardId")
    @JsonIgnore
    private Board board;
}
