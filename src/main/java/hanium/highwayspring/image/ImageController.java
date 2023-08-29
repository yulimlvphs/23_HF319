package hanium.highwayspring.image;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.DTO.BoardDTO;
import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {
    @Autowired
    imageService service;

    @PostMapping("/image")
    public ResponseDTO<?> createBoard(@RequestParam(value="image") List<MultipartFile> imageList) throws IOException {
        return ResponseDTO.success(service.upload(imageList));
    }

    /*@PostMapping("/transImage")
    public ResponseDTO<?> transImage(@RequestBody ImageRequestDTO requestDTO) {
        List<String> imageList = requestDTO.getImageList();
        return ResponseDTO.success(service.moveImagesToFinalLocation(imageList));
    }*/
}
