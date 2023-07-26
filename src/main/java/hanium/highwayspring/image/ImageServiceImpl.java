package hanium.highwayspring.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class ImageServiceImpl {

    // 의존성 주입
    private final JpaDiaryRepository diaryRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public ImageServiceImpl(JpaDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

   @Transactional
   public Long keepImage(MultipartFile image, Image entity) throws IOException {
       System.out.println("Image service saveDiary");
       if (!image.isEmpty()) {
           try {
               String storedFileName = s3Uploader.upload(image, "images");
               entity.setImageUrl(storedFileName);
           } catch (IOException e) {
               // 업로드 오류 처리
               e.printStackTrace();
               // 또는 로그로 기록: log.error("S3 파일 업로드에 실패했습니다. {}", e.getMessage());
               throw new IllegalStateException("S3 파일 업로드에 실패했습니다.");
           }
       }
       System.out.println("-------------"+entity.getImageUrl()+"---------------");
       Image savedImage = diaryRepository.save(entity);
       return savedImage.getId();
   }
}
