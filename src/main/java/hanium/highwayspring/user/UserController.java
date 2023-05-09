package hanium.highwayspring.user;

import hanium.highwayspring.security.TokenProvider;
import hanium.highwayspring.todo.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@Slf4j
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/join")
    public String createUserAPI(User user) {
        userService.join(user);
        return "success";
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> findUserAPI(@RequestParam("userNo") Long no) {
        return ResponseEntity.ok(userService.findOne(no));
    }

    @GetMapping("/login")
    public Long LoginAPI(@RequestParam("userId") String id, @RequestParam("userPw") String pw) {
        return userService.login(id, pw);
    }

    @GetMapping("/idCheck")
    public Boolean IdCheck(@RequestParam("userId") String id) {
        return userService.idCheck(id);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticate(@RequestParam("userId") String id, @RequestParam("userPw") String pw) {
        User user = userService.SignIn(id, pw);

        // 사용자의 id, pwd 일치할 경우
        if (user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .userEmail(user.getUserEmail())
                    .userId(user.getUserId())
                    .token(token)          //반환된 토큰 적용
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login faild.")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
