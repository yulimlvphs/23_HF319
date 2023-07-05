package hanium.highwayspring.school.heart;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class schoolHeartService {
    private final schoolHeartRepository heartRepository;

    public ResponseDTO<?> insert(final Heart heart){
        heartRepository.save(heart);

        schoolHeartDTO dto = schoolHeartDTO.builder()
                .id(heart.getId())
                .schoolId(heart.getSchool().getId())
                .uid(heart.getUser().getUid())
                .build();
        return ResponseDTO.success(dto);
    }

    public  ResponseDTO<?> delete(Long heartId){
        heartRepository.deleteById(heartId);
        return ResponseDTO.success("success");
    }

    public  ResponseDTO<List<Long>> findAll(Long userId){
        List<Heart> hearts = heartRepository.findByUserId(userId);
        Set<Long> schoolIds = new HashSet<>();
        for (Heart heart : hearts) {
            schoolIds.add(heart.getSchool().getId());
        }
        List<Long> schoolIdList = new ArrayList<>(schoolIds);
        return ResponseDTO.success(schoolIdList);
    }

    public boolean existsByUserIdAndSchoolId(Long userId, Long schoolId) {
        return heartRepository.existsByUserIdAndSchoolId(userId, schoolId);
    }

    public long countByUserId(Long userId) {
        return heartRepository.countByUserId(userId);
    }
}
