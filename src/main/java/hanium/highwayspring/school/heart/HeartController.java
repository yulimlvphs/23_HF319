package hanium.highwayspring.school.heart;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.School;
import hanium.highwayspring.school.SchoolService;
import hanium.highwayspring.user.User;
import hanium.highwayspring.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequestMapping("school/heart")
@RestController
@Slf4j
public class HeartController {
    private final HeartService heartService;
    private final UserService userService;
    private final SchoolService schoolService;

    public HeartController(HeartService heartService, UserService userService, SchoolService schoolService) {
        this.heartService = heartService;
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseDTO<?> addHeart(HttpServletRequest request, @RequestParam Long schoolId){
        User user = userService.getUser(request)
                .orElseThrow(()->new IllegalArgumentException("유저 정보가 없습니다."));
        Optional<School> school = schoolService.findBySchoolId(schoolId);
        Heart heart = Heart.builder()
                .user(user)
                .school(school.get())
                .build();
        return heartService.insert(heart);
    }

    @DeleteMapping
    public ResponseDTO<?> delHeart(HttpServletRequest request, @RequestParam Long heartId){
        return heartService.delete(heartId);
    }
}

