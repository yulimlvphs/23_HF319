package hanium.highwayspring.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TB_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String token;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;
    private String userSex;
    private Long userAge;
}
