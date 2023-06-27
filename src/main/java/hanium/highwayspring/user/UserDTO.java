package hanium.highwayspring.user;

import hanium.highwayspring.school.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO{
    private Long userNo;
    private String userId;
    private String userName;
    private String userEmail;
    private String userGender;
    private Long userAge;
    private Long userRole;

    private School school;

    private Long point;


    public UserDTO(final User entity) {
        this.userNo = entity.getId();
        this.userId = entity.getUid();
        this.userName = entity.getName();
        this.userEmail = entity.getEmail();
        this.userGender = entity.getGender();
        this.userAge = entity.getAge();
        this.userRole = entity.getRole();
    }

    public static UserDTO toEntity(final User entity) {
        return UserDTO.builder()
                .userNo(entity.getId())
                .userId(entity.getUid())
                .userName(entity.getName())
                .userEmail(entity.getEmail())
                .userGender(entity.getGender())
                .userAge(entity.getAge())
                .userRole(entity.getRole())
                .build();
    }
}

