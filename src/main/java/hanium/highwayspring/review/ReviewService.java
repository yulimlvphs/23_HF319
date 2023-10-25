package hanium.highwayspring.review;
import hanium.highwayspring.config.res.ResponseDTO;
import hanium.highwayspring.school.School;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public ResponseDTO<?> save(Review review){
        Review re = repository.save(review);
        return ResponseDTO.success(re);
    }

    public Optional<Review> findById(Long id){  //해당 학교 -> 학교에 해당하는 댓글 1개를 가져와서 update에 사용
        Optional<Review> re = repository.findById(id);
        return re;
    }

    @Transactional
    public ResponseDTO<?> update(Long id, ReviewDTO dto) {
        try {
            Review entity = this.repository.findById(id).orElse(null);

            if (entity == null) {
                return ResponseDTO.fail("isEmpty", "null");
            }

            if (dto.getContent() != null) {
                entity.setContent(dto.getContent());
            }
            if (dto.getTrafficRate() != null) {
                entity.setTrafficRate(dto.getTrafficRate());
            }
            if (dto.getFacilityRate() != null) {
                entity.setFacilityRate(dto.getFacilityRate());
            }
            if (dto.getCafeteriaRate() != null) {
                entity.setCafeteriaRate(dto.getCafeteriaRate());
            }
            if (dto.getEducationRate() != null) {
                entity.setEducationRate(dto.getEducationRate());
            }
            if (dto.getEmploymentRate() != null) {
                entity.setEmploymentRate(dto.getEmploymentRate());
            }

            this.repository.save(entity);

            return ResponseDTO.success(entity);
        } catch (Exception e) {
            return ResponseDTO.fail("error", e.getMessage());
        }
    }


    public ResponseDTO<?> findAll(School schoolId){ //학교 아이디를 가져와서 해당 학교에 대한 리뷰 전체를 보여줌
        List<Review> reviewAll = repository.findBySchoolId(schoolId);
        return ResponseDTO.success(reviewAll);
    }

    @Transactional
    public ResponseDTO<?> softDelete(Long id) {
        Optional<Review> entity = repository.findById(id);

        if (entity.isPresent()) {
            Review review = entity.get();
            review.setDeleted(true); // 해당 엔티티를 삭제하는 대신 "deleted" 속성을 ture로 변경
            repository.save(review);
            return ResponseDTO.success(id); // 이렇게 반환하는 이유 : 프론트에서 요청해서
        } else {
            return ResponseDTO.fail("not found", "Review not found with ID: " + id);
        }
    }

}
