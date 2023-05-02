package hanium.highwayspring.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
    }

    public Long join(User user) {
        String enPw = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(enPw);
        userRepository.save(user);
        return user.getUserNo();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userNo) {
        return userRepository.findById(userNo);
    }

    public Boolean login(String id, String pw) {
        User user = userRepository.findByUserId(id);
        if (user != null && passwordEncoder.matches(pw, user.getUserPw())) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean idCheck(String id){
        User user = userRepository.findByUserId(id);
        if(user == null)
            return true;
        else
            return false;
    }
}
