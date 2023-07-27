package hanium.highwayspring.user;

import hanium.highwayspring.config.res.ResponseDTO;
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

    @GetMapping("/test")
    public ResponseDTO<?> awsTest(){
        return ResponseDTO.success(true);
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest userRequest) throws Exception {
        return ResponseEntity.ok().body(userService.doLogin(userRequest));
    }

    //Access Token을 재발급 위한 api
    @PostMapping("/issue")
    public ResponseEntity issueAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(userService.issueAccessToken(request));
    }

    @GetMapping("/info")
    public ResponseDTO<?> findUserAPI(HttpServletRequest request) {
        return userService.findByToken(request);
    }

    @GetMapping("/idCheck")
    public ResponseDTO<?> IdCheck(@RequestParam("userId") String id) {
        return ResponseDTO.success(userService.idCheck(id));
    }

    @PutMapping("/updatePoint")
    public ResponseDTO<?> updatePoint(@RequestParam("point") Long point, HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return ResponseDTO.success(userService.updatePoint(user.getUid(), point));
    }
}
