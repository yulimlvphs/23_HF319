package hanium.highwayspring.review;
import hanium.highwayspring.school.School;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Review save(Review review){
        return repository.save(review);
    }

    public Optional<Review> findById(Long id){  //해당 학교 -> 학교에 해당하는 댓글 1개를 가져와서 update에 사용
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

    public List<Review> findAll(School schoolId){ //학교 아이디를 가져와서 해당 학교에 대한 리뷰 전체를 보여줌
        List<Review> re = repository.findBySchoolId(schoolId);
        return re;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
