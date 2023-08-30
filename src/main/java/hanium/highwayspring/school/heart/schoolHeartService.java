package hanium.highwayspring.school.heart;
import hanium.highwayspring.config.res.ResponseDTO;

import hanium.highwayspring.school.heart.DTO.SchoolHeartInsertDTO;
import hanium.highwayspring.school.heart.repository.schoolHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class schoolHeartService {
    private final schoolHeartRepository heartRepository;

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
        return heartRepository.findAll(userId);
    }

    public boolean existsByUserIdAndSchoolId(Long userId, Long schoolId) {
        return heartRepository.existsByUserIdAndSchoolId(userId, schoolId);
    }

    public long countByUserId(Long userId) {
        return heartRepository.countByUserId(userId);
    }
}
