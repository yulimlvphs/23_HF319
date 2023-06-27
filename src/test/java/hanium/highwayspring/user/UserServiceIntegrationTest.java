package hanium.highwayspring.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void 포인트변경(){
        System.out.println(userService.updatePoint("Lee", 20L));
    }
}
