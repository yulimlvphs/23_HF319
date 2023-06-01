package hanium.highwayspring.school;

import hanium.highwayspring.config.res.ResponseDTO;
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

    public ResponseDTO<?> findAll() {
        return ResponseDTO.success(schoolRepository.findAll());
    }

    public Optional<School> findBySchoolId(Long id){
        Optional<School> sch = schoolRepository.findById(id);
        return sch;
    }
}
