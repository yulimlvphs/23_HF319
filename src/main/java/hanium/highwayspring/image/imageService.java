package hanium.highwayspring.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
@Service
public class imageService {
    @Autowired
    private AmazonS3 amazonS3;

    private final AmazonS3Client amazonS3Client;
    private final imageRepository repository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    @Transactional
    public List<String> upload(List<MultipartFile> multipartFileList) throws IOException {
        List<String> uploadedFileNames = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            if (!multipartFile.isEmpty()) {
                if (!isValidImage(multipartFile.getInputStream())) { //유효한 이미지인지 검사
                    throw new IllegalArgumentException("Invalid image format");
                }

                long maxSizeInBytes = 5 * 1024 * 1024; // 10MB

                if (!isImageSizeValid(multipartFile, maxSizeInBytes)) {  //용량체크
                    throw new IllegalArgumentException("Image size exceeds the limit");
                }

                File uploadFile = convert(multipartFile)
                        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

                log.info("변환된 이미지 파일 이름: " + uploadFile.getName());

                String url = upload(uploadFile, "temporary"); //dirName = 이미지가 저장될 s3 폴더명

                uploadedFileNames.add(url);
            }
        }
        return uploadedFileNames;
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
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public boolean isValidImage(InputStream inputStream) {
        try {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return false; // 이미지가 올바르지 않을 경우
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isImageSizeValid(MultipartFile imageFile, long maxSizeInBytes) {
        return imageFile.getSize() <= maxSizeInBytes;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
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

    @Transactional
    public void updateImages(List<String> newImageList, Long boardId) {
        List<Image> imageList = repository.findAllByBoardId(boardId);

// 기존 이미지 리스트의 이름만 추출하여 리스트로 저장
        List<String> existingImageList = imageList.stream()
                .map(image -> getImageNameFromUrl(image.getImageUrl()))
                .collect(Collectors.toList());

        for(String aa : existingImageList){
            System.out.println("--------------------맨처음 newList : " +aa);
        }
        List<String> addedImages = new ArrayList<>();
        List<String> deletedImages = new ArrayList<>();

// 새로 추가된 이미지와 삭제된 이미지 찾기
        for (String imageUrl : newImageList) {
            String imageName = getImageNameFromUrl(imageUrl);
            if (!existingImageList.contains(imageName)) {
                addedImages.add(imageName);
                System.out.println("-----------------------------addedImage입니다 : " + imageName);
            }
        }

        for (String existingImage : existingImageList) {
            String imageName = getImageNameFromUrl(existingImage);
            boolean shouldDelete = true;

            for (String newImage : newImageList) {
                if (getImageNameFromUrl(newImage).equals(imageName)) {
                    shouldDelete = false;
                    break;
                }
            }

            if (shouldDelete) {
                deletedImages.add(imageName);
                System.out.println("-----------------------------deletedImage입니다 : " + imageName);
            }
        }

// 추가된 이미지 업로드 로직
        moveImagesToFinalLocation(addedImages, boardId);

// 삭제된 이미지 처리 로직
        if (!addedImages.containsAll(deletedImages)) {
            System.out.println("------------------------delete 메소드 실행");
            deleteImagesForUpdate(deletedImages, boardId);
        }
    }

    //-------------------------------------------------------------------------------------임시폴더 -> 정식폴더로 이동할 때 쓰는 메소드들
    public List<String> moveImagesToFinalLocation(List<String> imageUrls, Long boardId) { //정식 경로로 옮기는 메인 메소드
        List<String> finalImageUrls = new ArrayList<>();
        try {
            for (String imageUrl : imageUrls) {
                String imageName = getImageNameFromUrl(imageUrl);
                finalImageUrls.add(moveImageToFinalFolder(imageName));
            }
            saveImagesToDatabase(finalImageUrls, boardId);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 정보 출력
        }
        return finalImageUrls;
    }

    public void saveImagesToDatabase(List<String> finalImageUrls, Long boardId) { //정식 폴더로 이동할 이미지들은 db에 저장
        for (String imageUrl : finalImageUrls) {
            Image imageEntity = new Image();
            imageEntity.setImageUrl(imageUrl);
            imageEntity.setBoardId(boardId);
            repository.save(imageEntity);
        }
    }

    private String getImageNameFromUrl(String imageUrl) { // 이미지 URL에서 파일 이름 추출
        // 이미지 URL에서 파일 이름 추출
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    private String moveImageToFinalFolder(String imageName) { // 이미지를 임시 폴더에서 정식 폴더로 이동하는 로직 구현
        String sourceKey = "temporary/" + imageName;
        //원래 최종 목적지(정식 폴더)도 폴더로 구성했었는데 그러면 개별 삭제는 불가하다고 해서 루트에다가 저장.
        // Amazon S3의 copyObject 메소드를 활용하여 이미지를 임시 폴더에서 정식 폴더로 복사
        amazonS3.copyObject(bucket, sourceKey, bucket, imageName);

        // 복사가 완료되면 임시 폴더의 이미지 삭제
        amazonS3.deleteObject(bucket, sourceKey);

        // 정식 폴더에 저장된 이미지의 URL 생성 및 반환
        String finalImageUrl = amazonS3.getUrl(bucket, imageName).toString();

        return finalImageUrl;
    }

    //------------------------------------------------------------------------ delete 메소드들
    @Transactional
    public void deleteImages(Long boardId) {
        repository.deleteAllByBoardId(boardId); //같은 boardId를 가진 컬럼은 모두 삭제
        List<Image> imageList = repository.findAllByBoardId(boardId);
        List<String> existingImageList = new ArrayList<>();

        for (Image image : imageList) {
            existingImageList.add(image.getImageUrl());
        }
        for (String imageUrl : existingImageList) {
            String imageName = getImageNameFromUrl(imageUrl);
            deleteFile(imageName);
        }
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }

    @Transactional
    public void deleteImagesForUpdate(List<String> imageUrls, Long boardId) {  //update에서 사용될 delete 함수

        for (String imageUrl : imageUrls) {
            try {
                String newUrl = "https://highway-fe.s3.ap-northeast-2.amazonaws.com/" + imageUrl;
                System.out.println("-----------------------------------"+newUrl);
                repository.deleteImageForUpdate(boardId, newUrl);
                deleteFile(imageUrl);
            } catch (Exception e) {
                log.error("An error occurred while deleting image: " + imageUrl, e);
            }
        }
    }
}