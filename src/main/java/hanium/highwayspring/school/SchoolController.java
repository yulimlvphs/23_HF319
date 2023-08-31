package hanium.highwayspring.school;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/school")
@RestController
@Slf4j
public class SchoolController {
    @Autowired
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/list")
    public ResponseDTO<?> findSchoolList() {
        return schoolService.findAll();
    }

    @GetMapping("/info")
    public ResponseDTO<?> findSchool(@RequestParam("schId") Long id){
        return ResponseDTO.success(schoolService.getSchoolInfo(id));
    }
}