package hanium.highwayspring.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public String createUserAPI(User user) {
        userService.join(user);
        return "success";
    }

    @GetMapping("/info")
    public ResponseEntity<Optional<User>> findUserAPI(@RequestParam("userNo") Long no) {
        return ResponseEntity.ok(userService.findOne(no));
    }

    @GetMapping("/login")
    public User LoginAPI(@RequestParam("userId") String id, @RequestParam("userPw") String pw) {
        return userService.login(id,pw);
    }

    @GetMapping("/idCheck")
    public Boolean IdCheck(@RequestParam("userId") String id){
        return userService.idCheck(id);
    }
}
