package hanium.highwayspring.user.auth;

import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "AUTH_TB")
public class Auth {
// 사용자의 인증 정보를 담는 클래스이다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void accessUpdate(String accessToken) {
        this.accessToken = accessToken;
    }

    public void refreshUpdate(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
