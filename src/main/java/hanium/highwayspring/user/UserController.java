package hanium.highwayspring.user;

import hanium.highwayspring.config.res.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity join(User user) {
        if (userService.findByUserId(user.getUid()).isPresent()) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(userService.register(user));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(UserRequest userRequest) throws Exception {
        return ResponseEntity.ok().body(userService.doLogin(userRequest));
    }

    @PostMapping("/test")
    public Map userResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }

    //Access Token을 재발급 위한 api
    @PostMapping("/issue")
    public ResponseEntity issueAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(userService.issueAccessToken(request));
    }

    @GetMapping("/info")
    public ResponseEntity findUserAPI(HttpServletRequest request) {
        return userService.findByToken(request);
    }

    @GetMapping("/idCheck")
    public Boolean IdCheck(@RequestParam("userId") String id) {
        return userService.idCheck(id);
    }
}
