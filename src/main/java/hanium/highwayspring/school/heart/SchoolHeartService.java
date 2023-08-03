package hanium.highwayspring.school.heart;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.heart.dto.SchoolHeartInsertDTO;
import hanium.highwayspring.school.heart.repository.SchoolHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolHeartService {
    private final SchoolHeartRepository heartRepository;

    public ResponseDTO<?> insert(final Heart heart){
        heartRepository.save(heart);
        SchoolHeartInsertDTO dto = SchoolHeartInsertDTO.builder()
                .heartId(heart.getId())
                .schoolId(heart.getSchool().getId())
                .build();
        return ResponseDTO.success(dto);
    }

    public  ResponseDTO<?> delete(Long heartId){
        heartRepository.deleteById(heartId);
        return ResponseDTO.success("success");
    }

    public ResponseDTO<?> findAll(Long userId) {
        return ResponseDTO.success(heartRepository.findAllByUserId(userId));
    }

    public boolean existsByUserIdAndSchoolId(Long userId, Long schoolId) {
        return heartRepository.existsByUserIdAndSchoolId(userId, schoolId);
    }

    public long countByUserId(Long userId) {
        return heartRepository.countByUserId(userId);
    }
}
