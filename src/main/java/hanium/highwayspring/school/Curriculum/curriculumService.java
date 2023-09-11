package hanium.highwayspring.school.Curriculum;

import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class curriculumService {
    @Autowired
    private final curriculumRepository repository;

    public curriculumService(curriculumRepository repository) {
        this.repository = repository;
    }

    public List<String> curriculumList(Long schoolId){
        List<Curriculum> firstdto = repository.findAllBySchoolId(schoolId);
        List<String> contentList = new ArrayList<>();
        for (Curriculum dto : firstdto) {
            contentList.add(dto.getContent());
        }
        return contentList;
    }
}
