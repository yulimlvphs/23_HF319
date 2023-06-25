package hanium.highwayspring.school.heart;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    public ResponseDTO<?> insert(final Heart heart){
        heartRepository.save(heart);
        HeartDTO dto = HeartDTO.builder()
                .id(heart.getId())
                .schoolId(heart.getSchool().getId())
                .uid(heart.getUser().getUid())
                .build();
        return ResponseDTO.success(dto);
    }

    public  ResponseDTO<?> delete(Long heartId){
        heartRepository.deleteById(heartId);
        HeartDTO dto = HeartDTO.builder().build();
        return ResponseDTO.success(dto);
    }
}
