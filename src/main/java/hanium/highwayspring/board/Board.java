package hanium.highwayspring.board;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "board_TB")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id; 			//이 오브젝트의 아이디
	private String userId;
	private String title;
	private String content;
}
