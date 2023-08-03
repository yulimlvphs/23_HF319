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
public class SchoolHeartController {
    private final SchoolHeartService heartService;
    private final UserService userService;
    private final SchoolService schoolService;

    public SchoolHeartController(SchoolHeartService heartService, UserService userService, SchoolService schoolService) {
        this.heartService = heartService;
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseDTO<?> addHeart(HttpServletRequest request, @RequestParam Long schoolId){
        User user = userService.getUser(request)
                .orElseThrow(()->new IllegalArgumentException("유저 정보가 없습니다."));
        Optional<School> school = schoolService.findBySchoolId(schoolId);
        // 중복 체크 (한번 찜한 학교는 다시 할 수 없음)
        if (heartService.countByUserId(user.getId()) >= 10) {
            throw new IllegalArgumentException("이미 10개 이상의 학교를 찜하였습니다.");
        }

        // 중복 체크 (한번 찜한 학교는 다시 할 수 없음)
        if (!heartService.existsByUserIdAndSchoolId(user.getId(), schoolId)) {
            Heart heart = Heart.builder()
                    .user(user)
                    .school(school.get())
                    .build();
            return heartService.insert(heart);
        } else {
            throw new IllegalArgumentException("해당 학교는 이미 찜한 학교입니다.");
        }
    }

    @GetMapping
    public  ResponseDTO<?> getHeartSchoolAll(HttpServletRequest request) {
        User user = userService.getUser(request)
                .orElseThrow(()->new IllegalArgumentException("유저 정보가 없습니다."));
        return heartService.findAll(user.getId());
    }

    @DeleteMapping
    public ResponseDTO<?> deleteHeart(HttpServletRequest request, @RequestParam Long heartId){
        return heartService.delete(heartId);
    }
}

