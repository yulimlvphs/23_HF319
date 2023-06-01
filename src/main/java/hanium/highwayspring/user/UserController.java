package hanium.highwayspring.user;

import hanium.highwayspring.config.res.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity join(User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(UserRequest userRequest) throws Exception {
        return ResponseEntity.ok().body(userService.doLogin(userRequest));
    }

    //Access Token을 재발급 위한 api
    @PostMapping("/issue")
    public ResponseEntity issueAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(userService.issueAccessToken(request));
    }

    @GetMapping("/info")
    public ResponseEntity findUserAPI(HttpServletRequest request) {
        return ResponseEntity.ok().body(userService.findByToken(request));
    }

    @GetMapping("/idCheck")
      public Boolean IdCheck(@RequestParam("userId") String id) {
        return userService.idCheck(id);
    }
}
