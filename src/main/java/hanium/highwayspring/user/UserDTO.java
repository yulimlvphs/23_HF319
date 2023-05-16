package hanium.highwayspring.user;

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
public class UserDTO implements UserDetails {
    private Long userNo;
    private String userId;
    private String token;
    private String userName;
    private String userEmail;
    private String userGender;
    private Long userAge;
    private Long userRole;
    private boolean locked;	//계정 잠김 여부
    private Collection<GrantedAuthority> authorities; //권한 목록


    public UserDTO(final User entity) {
        this.userNo = entity.getId();
        this.userId = entity.getUid();
        this.userName = entity.getName();
        this.userEmail = entity.getEmail();
        this.userGender = entity.getGender();
        this.userAge = entity.getAge();
        this.userRole = entity.getRole();
    }

    public static UserDTO toEntity(final Optional<User> entity) {
        return UserDTO.builder()
                .userNo(entity.get().getId())
                .userId(entity.get().getUid())
                .userName(entity.get().getName())
                .userEmail(entity.get().getEmail())
                .userGender(entity.get().getGender())
                .userAge(entity.get().getAge())
                .userRole(entity.get().getRole())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//계정의 권한 목록을 리턴
        return authorities;
    }

    @Override
    public String getPassword() { //계정의 비밀번호를 리턴
        return null;
    }

    @Override
    public String getUsername() { //계정의 고유한 값을 리턴 (ex. DB의 PK값)
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() { //계정의 만료 여부 리턴
        return true; // 만료 안됨
    }

    @Override
    public boolean isAccountNonLocked() { //계정의 잠김 여부 리턴
        return true; //잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호 만료 여부 리턴
        return true; //만료 안됨
    }

    @Override
    public boolean isEnabled() { //계정의 활성화 여부 리턴
        return true; // 활성화
    }
}

