package hanium.highwayspring.user;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "TB_USER")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;
    private String userSex;
    private Long userAge;
}
