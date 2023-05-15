package hanium.highwayspring.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long userNo;
    private String userId;
    private String token;
    private String userName;
    private String userEmail;
    private String userSex;
    private Long userAge;

    public UserDTO(final User entity) {
        this.userNo = entity.getUserNo();
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.userEmail = entity.getUserEmail();
        this.userSex = entity.getUserSex();
        this.userAge = entity.getUserAge();
    }

    public static UserDTO toEntity(final Optional<User> entity) {
        return UserDTO.builder()
                .userNo(entity.get().getUserNo())
                .userId(entity.get().getUserId())
                .userName(entity.get().getUserName())
                .userEmail(entity.get().getUserEmail())
                .userSex(entity.get().getUserSex())
                .userAge(entity.get().getUserAge())
                .build();
    }
}
