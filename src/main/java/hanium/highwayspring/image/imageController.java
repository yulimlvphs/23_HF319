package hanium.highwayspring.image;

import hanium.highwayspring.config.res.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class imageController {
    private final imageService s3Uploader;

    @Autowired
    public imageController(imageService s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @ResponseBody
    @PostMapping(value="/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<?> saveDiary(HttpServletRequest request, @RequestParam(value="image") List<MultipartFile> imageList) throws IOException {
        s3Uploader.upload(imageList,1L);
        return ResponseDTO.success("success");
    }

}
