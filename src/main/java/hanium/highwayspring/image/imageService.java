package hanium.highwayspring.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
@Service
public class imageService {
    private final AmazonS3Client amazonS3Client;
    private final imageRepository repository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    @Transactional
    public void upload(List<MultipartFile> multipartFileList, Long boardId) throws IOException {
        for(MultipartFile multipartFile : multipartFileList) {
            if(!multipartFile.isEmpty()){
                // 메타데이터 설정
                File uploadFile = convert(multipartFile)
                        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
                log.info("변환된 이미지 파일 이름: " + uploadFile.getName());
                String url = upload(uploadFile, "static"); //dirName = 이미지가 저장될 s3 폴더명
                Image img = new Image(boardId, url);
                log.info("------------------"+img.getImageUrl()+"-------------");
                repository.save(img);
            }
        }
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "." + uploadFile.getName(); //이름 중복 방지를 위한 렌덤 코드를 추가(UUID.randomUUID())
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl; //이미지 url 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            // 변환에 실패하면 예외 처리
            convertFile.delete();
            // 예외 메시지와 스택 트레이스를 로그로 출력
            log.error("MultipartFile 변환에 실패했습니다: {}", e.getMessage(), e);
            return Optional.empty();
        }

        return Optional.of(convertFile);
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }

}
