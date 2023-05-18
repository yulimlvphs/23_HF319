package hanium.highwayspring.school;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public ResponseEntity findAll() {
        List<School> schoolList = schoolRepository.findAll();
        return ResponseEntity.ok().body(schoolList);
    }

    public ResponseEntity findBySchoolId(Long id){
        Optional<School> sch = schoolRepository.findById(id);
        return ResponseEntity.ok().body(sch);
    }
}
