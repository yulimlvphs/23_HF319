package hanium.highwayspring.board.heart;

import hanium.highwayspring.config.res.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    public ResponseDTO<?> insert(final Heart entity){
        heartRepository.save(entity);
        return ResponseDTO.success(entity);
    }
    public ResponseDTO<?> delete(final Heart entity){
        heartRepository.delete(entity);
        return ResponseDTO.success(entity);
    }
}