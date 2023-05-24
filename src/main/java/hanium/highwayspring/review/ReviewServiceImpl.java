package hanium.highwayspring.review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository repository;

    @Override
    public Review save(Review re){
        return repository.save(re);
    }

    @Override
    public Optional<Review> findById(Long id){
        Optional<Review> re = repository.findById(id);
        return re;
    }

    public Optional<Review> update(Long id, ReviewDTO dto){
        Optional<Review> entity = this.repository.findById(id);
        entity.ifPresent(t ->{
            // 내용이 널이 아니라면 엔티티의 객체를 바꿔준다.
            if(dto.getAuthor() != null) {
                t.setAuthor(dto.getAuthor());
            }
            if(dto.getTags() != null) {
                t.setTags(dto.getTags());
            }
            if(dto.getContent() != null) {
                t.setContent(dto.getContent());
            }
            if(dto.getAuthor() != null) {
                t.setAuthor(dto.getAuthor());
            }
            if(dto.getTrafficRate() != null) {
                t.setTrafficRate(dto.getTrafficRate());
            }
            if(dto.getFacilityRate() != null) {
                t.setFacilityRate(dto.getFacilityRate());
            }
            if(dto.getCafeteriaRate() != null) {
                t.setCafeteriaRate(dto.getCafeteriaRate());
            }
            if(dto.getEducationRate() != null) {
                t.setEducationRate(dto.getEducationRate());
            }
            if(dto.getEmploymentRate() != null) {
                t.setEmploymentRate(dto.getEmploymentRate());
            }
            // 이걸 실행하면 idx 때문에 update가 실행됩니다.
            this.repository.save(t);
        });
        return entity;
    }

    @Override
    public List<Review> findAll(Long schoolId) {
        List<Review> re = repository.findBySchoolId(schoolId);
        return re;
    }

    @Override
    public void deleteReview(Long id) {
        repository.deleteById(id);
    }
}
