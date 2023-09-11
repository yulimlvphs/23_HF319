package hanium.highwayspring.school.Curriculum;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/school")
@RestController
@Slf4j
public class curriculumController {

    @Autowired
    private curriculumService cservice;

    @GetMapping("/curri/list/{schoolId}")
    public ResponseDTO<?> curriculumList(@PathVariable Long schoolId){
        return ResponseDTO.success(cservice.curriculumList(schoolId));
    }
}
