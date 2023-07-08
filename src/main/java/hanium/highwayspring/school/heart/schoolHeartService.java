package hanium.highwayspring.school.heart;
import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class schoolHeartService {
    private final schoolHeartRepository heartRepository;

    public ResponseDTO<?> insert(final Heart heart){
        heartRepository.save(heart);

        schoolHeartDTO dto = schoolHeartDTO.builder()
                .heartId(heart.getId())
                .schoolId(heart.getSchool().getId())
                .build();
        return ResponseDTO.success(dto);
    }

    public  ResponseDTO<?> delete(Long heartId){
        heartRepository.deleteById(heartId);
        return ResponseDTO.success("success");
    }

    public  ResponseDTO<?> findAll(Long userId){
        List<Heart> hearts = heartRepository.findAllByUserId(userId);
        List<schoolHeartDTO> heartDTOs = new ArrayList<>();

        for (Heart heart : hearts) {
            schoolHeartDTO dto = new schoolHeartDTO();
            dto.setHeartId(heart.getId());
            dto.setSchoolId(heart.getSchool().getId());
            heartDTOs.add(dto);
        }
        return ResponseDTO.success(heartDTOs);
    }

    public boolean existsByUserIdAndSchoolId(Long userId, Long schoolId) {
        return heartRepository.existsByUserIdAndSchoolId(userId, schoolId);
    }

    public long countByUserId(Long userId) {
        return heartRepository.countByUserId(userId);
    }
}
