package hanium.highwayspring.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
    }

    public Long join(User user) {
        String enPw = passwordEncoder.encode(user.getPwd());
        user.setPwd(enPw);
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public UserDTO findOne(Long userNo) {
        Optional<User> user = userRepository.findById(userNo);
        UserDTO dto = UserDTO.toEntity(user);
        return dto;
    }

    public Long login(String id, String pw) {
        User user = userRepository.findByUid(id);
        if (user != null && passwordEncoder.matches(pw, user.getPwd())) {
            return user.getId();
        } else {
            return (long) -1;
        }
    }

    public User SignIn(String id, String pw) {
        User user = userRepository.findByUid(id);
        if (user != null && passwordEncoder.matches(pw, user.getPwd())) {
            return user;
        } else {
            return null;
        }
    }

    public Boolean idCheck(String id) {
        User user = userRepository.findByUid(id);
        if (user == null && id.length() > 0)
            return true;
        else
            return false;
    }
}
