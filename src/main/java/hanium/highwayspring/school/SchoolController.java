package hanium.highwayspring.school;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/school")
@RestController
@Slf4j
public class SchoolController {
    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/list")
    public ResponseEntity findSchoolList() {
        return schoolService.findAll();
    }

    @GetMapping("/info")
    public ResponseEntity findSchool(@RequestParam("schId") Long id){
        return schoolService.findBySchoolId(id);
    }
}